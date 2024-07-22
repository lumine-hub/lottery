package com.xlm.domain.service.rule.tree.impl;

import com.xlm.domain.model.vo.RuleLogicCheckTypeVO;
import com.xlm.domain.service.rule.tree.ILogicTreeNode;
import com.xlm.domain.service.rule.tree.factory.DefaultTreeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author xlm
 * 2024/7/22 下午5:21
 * 兜底抽奖
 */
@Slf4j
@Component("rule_luck_award")
public class RuleLuckAwardLogicTreeNode implements ILogicTreeNode {

    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId) {
        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.TAKE_OVER)
                .strategyAwardVo(DefaultTreeFactory.StrategyAwardVo.builder()
                        .awardId(101)
                        .awardRuleValue("1,100")
                        .build())
                .build();
    }

}