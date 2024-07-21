package com.xlm.domain.service.rule.chain;

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
     * @return 奖品id awardId
     */
    Integer logic(String userId,Long strategyId);
}
