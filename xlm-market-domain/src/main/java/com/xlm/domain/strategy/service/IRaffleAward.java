package com.xlm.domain.strategy.service;

import com.xlm.domain.strategy.model.entity.StrategyAwardEntity;

import java.util.List;

/**
 * @author xlm
 * 2024/7/24 下午5:24
 */
public interface IRaffleAward {

    List<StrategyAwardEntity> queryRaffleStrategyAwardList(Long strategyId);
}
