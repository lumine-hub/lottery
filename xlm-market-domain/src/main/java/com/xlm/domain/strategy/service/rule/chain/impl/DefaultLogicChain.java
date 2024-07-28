package com.xlm.domain.strategy.service.rule.chain.impl;

import com.xlm.domain.strategy.service.armory.IStrategyDispatch;
import com.xlm.domain.strategy.service.rule.chain.AbstractLogicChain;
import com.xlm.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author xlm
 * 2024/7/21 下午3:39
 * 【默认抽奖规则】 在经过了前面的黑名单等过滤后，责任链处理后最后的兜底抽奖规则。
 */
@Slf4j
@Component("default")
public class DefaultLogicChain extends AbstractLogicChain {

    @Resource
    private IStrategyDispatch strategyDispatch;
    @Override
    public DefaultChainFactory.StrategyAwardVO logic(String userId, Long strategyId) {
        Integer awardId = strategyDispatch.getRandomAwardId(strategyId);
        log.info("【默认抽奖规则】userId:{},strategyId:{},awardId:{}", userId, strategyId, awardId);
        return DefaultChainFactory.StrategyAwardVO.builder()
                .awardId(awardId)
                .logicModel(ruleModel())
                .build();
    }

    @Override
    protected String ruleModel() {
        return DefaultChainFactory.LogicModel.RULE_DEFAULT.getCode();
    }
}
