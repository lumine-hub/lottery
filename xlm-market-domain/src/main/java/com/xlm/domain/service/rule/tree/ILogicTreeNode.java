package com.xlm.domain.service.rule.tree;

import com.xlm.domain.service.rule.tree.factory.DefaultTreeFactory;

/**
 * @author xlm
 * 2024/7/22 下午5:17
 * 当抽完奖获取到awardId后，会进入该奖品的规则处理，比如rule_lock、rule_luck_award等
 */
public interface ILogicTreeNode {
    /**
     *
     * @param userId
     * @param strategyId
     * @param awardId
     * @return 过滤结果：应包含放行还是接管、awardId、ruleId
     */
    DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId);

}
