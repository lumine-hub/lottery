package com.xlm.domain.service;

import com.xlm.domain.model.entity.RaffleAwardEntity;
import com.xlm.domain.model.entity.RaffleFactorEntity;
import com.xlm.domain.model.entity.RuleActionEntity;
import com.xlm.domain.model.vo.RuleLogicCheckTypeVO;
import com.xlm.domain.model.vo.StrategyAwardRuleModelVO;
import com.xlm.domain.repository.IStrategyRepository;
import com.xlm.domain.service.armory.IStrategyDispatch;
import com.xlm.domain.service.rule.chain.ILogicChain;
import com.xlm.domain.service.rule.chain.factory.DefaultChainFactory;
import com.xlm.types.enums.ResponseCode;
import com.xlm.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;

/**
 * @author xlm
 * 2024/7/19 下午5:46
 * 抽奖策略抽象类，定义抽奖的标准流程
 */
@Slf4j
public abstract class AbstractRaffleStrategy implements IRaffleStrategy {

    @Resource
    private DefaultChainFactory chainFactory;

    // 策略仓储服务 -> domain层像一个大厨，仓储层提供米面粮油
    protected IStrategyRepository repository;
    // 策略调度服务 -> 只负责抽奖处理，通过新增接口的方式，隔离职责，不需要使用方关心或者调用抽奖的初始化
    protected IStrategyDispatch strategyDispatch;

    public AbstractRaffleStrategy(IStrategyRepository repository, IStrategyDispatch strategyDispatch) {
        this.repository = repository;
        this.strategyDispatch = strategyDispatch;
    }

    @Override
    public RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity) {
        // 1. 参数校验
        String userId = raffleFactorEntity.getUserId();
        Long strategyId = raffleFactorEntity.getStrategyId();
        if (null == strategyId || StringUtils.isBlank(userId)) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
        }

        // 2. 获取抽奖id
        ILogicChain logicChain = chainFactory.openLogicChain(strategyId);
        Integer awardId = logicChain.logic(userId, strategyId);


        // 3. 查询奖品规则「抽奖中（拿到奖品ID时，过滤规则）、抽奖后（扣减完奖品库存后过滤，抽奖中拦截和无库存则走兜底）」
        StrategyAwardRuleModelVO ruleModel = repository.queryStrategyAwardRuleModelVO(strategyId, awardId);


        // 4. 抽奖中 - 规则过滤
        RuleActionEntity<RuleActionEntity.RaffleCenterEntity> ruleActionCenterEntity = this.doCheckRaffleCenterLogic(RaffleFactorEntity.builder()
                .strategyId(strategyId)
                .awardId(awardId)
                .userId(userId).build(), ruleModel.raffleCenterRuleModelList());

        // 经抽奖中规则过滤后，不能放行，比如抽到的奖品未解锁
        if (ruleActionCenterEntity.getCode().equals(RuleLogicCheckTypeVO.TAKE_OVER.getCode())) {
            log.info("【规则中拦截】,规则rule_lock拦截");
            return RaffleAwardEntity.builder()
                    .strategyId(strategyId)
                    .awardDesc("中奖中规则拦截，通过抽奖后规则 rule_luck_award 走兜底奖励。")
                    .build(); // 目前没有奖励，后续过滤后可能会增加兜底奖励。

        }
        return RaffleAwardEntity.builder()
                .strategyId(strategyId)
                .awardId(awardId)
                .build();
    }

    protected abstract RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> doCheckRaffleBeforeLogic(RaffleFactorEntity raffleFactorEntity, String... logics);

    protected abstract RuleActionEntity<RuleActionEntity.RaffleCenterEntity> doCheckRaffleCenterLogic(RaffleFactorEntity raffleFactorEntity, String... logics);
}

