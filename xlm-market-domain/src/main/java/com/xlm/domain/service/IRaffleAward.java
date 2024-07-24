package com.xlm.domain.service;

import com.xlm.domain.model.entity.StrategyAwardEntity;

import java.util.List;

/**
 * @author xlm
 * 2024/7/24 下午5:24
 */
public interface IRaffleAward {

    List<StrategyAwardEntity> queryRaffleStrategyAwardList(Long strategyId);
}
