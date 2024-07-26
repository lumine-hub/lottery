package com.xlm.domain.service.raffle;

import com.xlm.domain.model.entity.StrategyAwardEntity;
import com.xlm.domain.model.vo.RuleTreeVO;
import com.xlm.domain.model.vo.StrategyAwardRuleModelVO;
import com.xlm.domain.model.vo.StrategyAwardStockKeyVO;
import com.xlm.domain.repository.IStrategyRepository;
import com.xlm.domain.service.AbstractRaffleStrategy;
import com.xlm.domain.service.IRaffleAward;
import com.xlm.domain.service.IRaffleStock;
import com.xlm.domain.service.armory.IStrategyDispatch;
import com.xlm.domain.service.rule.chain.ILogicChain;
import com.xlm.domain.service.rule.chain.factory.DefaultChainFactory;
import com.xlm.domain.service.rule.tree.factory.DefaultTreeFactory;
import com.xlm.domain.service.rule.tree.factory.engine.IDecisionTreeEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


/**
 * @author xlm
 * 2024/7/19 下午7:06
 * 默认的抽奖策略实现
 */
@Slf4j
@Service
public class DefaultRaffleStrategy extends AbstractRaffleStrategy implements IRaffleAward, IRaffleStock {

    public DefaultRaffleStrategy(IStrategyRepository repository, IStrategyDispatch strategyDispatch, DefaultChainFactory defaultChainFactory, DefaultTreeFactory defaultTreeFactory) {
        super(repository, strategyDispatch, defaultChainFactory, defaultTreeFactory);
    }

    @Override
    public DefaultChainFactory.StrategyAwardVO raffleLogicChain(String userId, Long strategyId) {
        ILogicChain logicChain = defaultChainFactory.openLogicChain(strategyId);
        return logicChain.logic(userId, strategyId);
    }

    @Override
    public DefaultTreeFactory.StrategyAwardVO raffleLogicTree(String userId, Long strategyId, Integer awardId) {
        // 1.查询抽到的awardId对应的strategy_award数据
        StrategyAwardRuleModelVO strategyAwardRuleModelVO = repository.queryStrategyAwardRuleModelVO(strategyId, awardId);
        if (null == strategyAwardRuleModelVO) {
            return DefaultTreeFactory.StrategyAwardVO.builder().awardId(awardId).build();
        }
        // 2.根据strategy_award数据的rule_model字段(就是tree_id)从rule_tree、rule_tree_node、rule_tree_line
        // 中得到整条决策树树的信息，这个整条输的信息在下面的repository里实现，主要三表连查
        RuleTreeVO ruleTreeVO = repository.queryRuleTreeVOByTreeId(strategyAwardRuleModelVO.getRuleModels());
        if (null == ruleTreeVO) {
            throw new RuntimeException("存在抽奖策略配置的规则模型 Key，未在库表 rule_tree、rule_tree_node、rule_tree_line 配置对应的规则树信息 " + strategyAwardRuleModelVO.getRuleModels());
        }
        // 3.根据tree的详细信息，得到执行引擎，这个执行引擎顾名思义，就是执行这棵树的。
        IDecisionTreeEngine treeEngine = defaultTreeFactory.openLogicTree(ruleTreeVO);
        // 4.执行
        return treeEngine.process(userId, strategyId, awardId);
    }

    @Override
    public StrategyAwardStockKeyVO takeQueueValue() throws InterruptedException {
        return repository.takeQueueValue();
    }

    @Override
    public void updateStrategyAwardStock(Long strategyId, Integer awardId) {
        repository.updateStrategyAwardStock(strategyId, awardId);
    }

    @Override
    public List<StrategyAwardEntity> queryRaffleStrategyAwardList(Long strategyId) {
        return repository.queryStrategyAwardList(strategyId);
    }
}