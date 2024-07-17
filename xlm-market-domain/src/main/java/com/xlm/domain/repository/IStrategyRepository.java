package com.xlm.domain.repository;

import com.xlm.domain.model.StrategyAwardEntity;

import java.util.List;
import java.util.Map;

/**
 * @author xlm
 * 2024/7/17 下午1:43
 */
public interface IStrategyRepository {

    List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId);

    void storeStrategyAwardSearchRateTable(Long strategyId, Integer rateRange, Map<Integer, Integer> strategyAwardSearchRateTable);

    Integer getStrategyAwardAssemble(Long strategyId, Integer rateKey);

    int getRateRange(Long strategyId);

}