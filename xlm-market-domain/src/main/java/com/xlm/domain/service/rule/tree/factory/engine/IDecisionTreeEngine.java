package com.xlm.domain.service.rule.tree.factory.engine;

import com.xlm.domain.service.rule.tree.factory.DefaultTreeFactory;

/**
 * @author xlm
 * 2024/7/21 下午5:49
 * 执行决策树的方法，
 */
public interface IDecisionTreeEngine {

    DefaultTreeFactory.StrategyAwardData process(String userId, Long strategyId, Integer awardId);

}