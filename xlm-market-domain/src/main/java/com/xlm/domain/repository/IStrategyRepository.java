package com.xlm.domain.repository;

import com.xlm.domain.model.entity.StrategyAwardEntity;
import com.xlm.domain.model.entity.StrategyEntity;
import com.xlm.domain.model.entity.StrategyRuleEntity;
import com.xlm.domain.model.vo.StrategyAwardRuleModelVO;

import java.util.List;
import java.util.Map;

/**
 * @author xlm
 * 2024/7/17 下午1:43
 */
public interface IStrategyRepository {

    List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId);

    void storeStrategyAwardSearchRateTable(String key, Integer rateRange, Map<Integer, Integer> strategyAwardSearchRateTable);

    Integer getStrategyAwardAssemble(Long strategyId, Integer rateKey);

    Integer getStrategyAwardAssemble(String key, Integer rateKey);

    int getRateRange(Long strategyId);

    int getRateRange(String key);

    StrategyEntity queryStrategyEntityByStrategyId(Long strategyId);

    StrategyRuleEntity queryStrategyRule(Long strategyId, String ruleWeight);

    String queryStrategyRuleValue(Long strategyId, Integer awardId, String ruleModel);
    String queryStrategyRuleValue(Long strategyId, String ruleModel);
    StrategyAwardRuleModelVO queryStrategyAwardRuleModelVO(Long strategyId, Integer awardId);
}