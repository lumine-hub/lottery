package com.xlm.domain.strategy.service.rule.chain;

import com.xlm.domain.strategy.service.rule.chain.factory.DefaultChainFactory;

/**
 * @author xlm
 * 2024/7/21 下午3:23
 * 抽奖策略规则责任链接口
 */

public interface ILogicChain extends ILogicChainArmory {

    /**
     *
     * @param strategyId
     * @param userId
     * @return 奖品对象
     */
    DefaultChainFactory.StrategyAwardVO logic(String userId, Long strategyId);
}
