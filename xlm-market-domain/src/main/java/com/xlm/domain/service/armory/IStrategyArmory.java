package com.xlm.domain.service.armory;

import java.util.List;

/**
 * @author xlm
 * 2024/7/17 下午1:43
 * 策略装配库(兵工厂)，负责初始化策略计算
 */
public interface IStrategyArmory {

    /**
     * 装配抽奖策略配置「触发的时机可以为活动审核通过后进行调用」
     *
     * @param strategyId 策略ID
     * @return 装配结果
     */
    boolean assembleLotteryStrategy(Long strategyId);

    /**
     * 进行抽奖，获取概率值奖品查找表的结果
     *
     * @param strategyId
     * @return
     */
    Integer getRandomAwardId(Long strategyId);
}
