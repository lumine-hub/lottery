package com.xlm.domain.strategy.service.rule.tree.factory;

import com.xlm.domain.strategy.model.vo.RuleLogicCheckTypeVO;
import com.xlm.domain.strategy.model.vo.RuleTreeVO;
import com.xlm.domain.strategy.service.rule.tree.ILogicTreeNode;
import com.xlm.domain.strategy.service.rule.tree.factory.engine.IDecisionTreeEngine;
import com.xlm.domain.strategy.service.rule.tree.factory.engine.impl.DecisionTreeEngine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
/**
 * @author xlm
 * 2024/7/21 下午5:34
 * 决策树工厂：支付提供ILogicTreeNode的实现类。不负责执行整个流程，需要engine去执行。
 */
@Service
public class DefaultTreeFactory {

    private final Map<String, ILogicTreeNode> logicTreeNodeGroup;

    public DefaultTreeFactory(Map<String, ILogicTreeNode> logicTreeNodeGroup) {
        this.logicTreeNodeGroup = logicTreeNodeGroup;
    }

    public IDecisionTreeEngine openLogicTree(RuleTreeVO ruleTreeVO) {
        return new DecisionTreeEngine(logicTreeNodeGroup, ruleTreeVO);
    }

    /**
     * 决策树个动作实习
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TreeActionEntity {
        private RuleLogicCheckTypeVO ruleLogicCheckType;
        private StrategyAwardVO strategyAwardVO;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StrategyAwardVO {
        /** 抽奖奖品ID - 内部流转使用 */
        private Integer awardId;
        /** 抽奖奖品规则 */
        private String awardRuleValue;
    }

}
