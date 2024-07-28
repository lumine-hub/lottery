package com.xlm.domain.strategy.service.rule.chain.impl;

import com.xlm.domain.strategy.repository.IStrategyRepository;
import com.xlm.domain.strategy.service.armory.IStrategyDispatch;
import com.xlm.domain.strategy.service.rule.chain.AbstractLogicChain;
import com.xlm.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import com.xlm.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xlm
 * 2024/7/21 下午3:48
 * 规则权重的过滤链
 */
@Slf4j
@Component("rule_weight")
public class RuleWeightLogicChain extends AbstractLogicChain {
    
    @Resource
    private IStrategyRepository repository;

    @Resource
    private IStrategyDispatch strategyDispatch;

    // 模拟用户积分
    public Long userScore = 0L;

    /**
     * 权重责任链过滤；
     * 1. 权重规则格式；4000:102,103,104,105 5000:102,103,104,105,106,107 6000:102,103,104,105,106,107,108,109
     * 2. 解析数据格式；判断哪个范围符合用户的特定抽奖范围
     */
    @Override
    public DefaultChainFactory.StrategyAwardVO logic(String userId, Long strategyId) {
        log.info("RuleWeightLogicChain start strategyId:{},userId:{}", strategyId, userId);
        // ruleValue格式 4000:102,103,104,105 5000:102,103,104,105,106,107 6000:102,103,104,105,106,107,108,109
        String ruleValue = repository.queryStrategyRuleValue(strategyId, ruleModel());
        // key:4000 value:102,103,104,105
        Map<Long,String> ruleValueMap = getRuleValueMap(ruleValue);
        if (ruleValueMap.isEmpty()) return next().logic(userId, strategyId);

        // 排序key的值
        List<Long> keyList = ruleValueMap.keySet().stream().sorted().collect(Collectors.toList());
        // 找出符合的值，比如keyList[3000,4000,5000,6000]，用户积分为3500，那么就是找到3000
        Long key = keyList.stream()
                .sorted(Comparator.reverseOrder())
                .filter(k -> k <= userScore)
                .findFirst()
                .orElse(null);
        if (key != null) {
            String ruleWeightValue = ruleValueMap.get(key);
            Integer awardId = strategyDispatch.getRandomAwardId(strategyId, ruleWeightValue);
            log.info("抽奖责任链-权重接管 userId: {} strategyId: {} ruleModel: {} awardId: {}", userId, strategyId, ruleModel(), awardId);
            return DefaultChainFactory.StrategyAwardVO.builder()
                    .awardId(awardId)
                    .logicModel(ruleModel())
                    .build();
        }
        //用户积分哪个都不够，放行
        return next().logic(userId, strategyId);
    }

    private Map<Long, String> getRuleValueMap(String ruleValue) {
        String[] ruleValueGroups = ruleValue.split(Constants.SPACE);
        Map<Long,String> ruleValueMap = new HashMap<>();
        for (String rule : ruleValueGroups) {
            if (rule == null|| rule.isEmpty()) continue;
            String[] parts = rule.split(Constants.COLON);
            if (parts.length != 2) {
                throw new IllegalArgumentException("rule_weight rule_rule invalid input format" + rule);
            }
            ruleValueMap.put(Long.parseLong(parts[0]), rule);
        }
        return ruleValueMap;
    }

    @Override
    protected String ruleModel() {
        return DefaultChainFactory.LogicModel.RULE_WEIGHT.getCode();
    }
}
