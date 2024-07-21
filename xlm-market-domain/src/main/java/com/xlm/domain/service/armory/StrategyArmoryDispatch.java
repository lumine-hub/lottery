package com.xlm.domain.service.armory;

import com.xlm.domain.model.entity.StrategyAwardEntity;
import com.xlm.domain.model.entity.StrategyEntity;
import com.xlm.domain.model.entity.StrategyRuleEntity;
import com.xlm.domain.repository.IStrategyRepository;
import com.xlm.types.enums.ResponseCode;
import com.xlm.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.*;

/**
 * @author xlm
 * 2024/7/17 下午2:08
 */
@Slf4j
@Service
public class StrategyArmoryDispatch implements IStrategyArmory, IStrategyDispatch{

    @Resource
    private IStrategyRepository repository;

    // 构造一个map，key是要出的点数，value对应的奖励。并将map、value、策略id的范围放入redis
    @Override
    public boolean assembleLotteryStrategy(Long strategyId) {
        //1.根据strategyId查询策略配置
        List<StrategyAwardEntity> strategyAwardEntities = repository.queryStrategyAwardList(strategyId);
        //2.保存配置信息
        assembleLotteryStrategy(String.valueOf(strategyId), strategyAwardEntities);
        //3.权重策略配置 - 适用于 rule_weight 权重规则配置
        StrategyEntity strategyEntity = repository.queryStrategyEntityByStrategyId(strategyId);
        if (strategyEntity == null || strategyEntity.getRuleWeight() == null) return true;
        String ruleWeight = strategyEntity.getRuleWeight();
        StrategyRuleEntity strategyRuleEntity = repository.queryStrategyRule(strategyId, ruleWeight);
        if (null == strategyRuleEntity) {
            throw new AppException(ResponseCode.STRATEGY_RULE_WEIGHT_IS_NULL.getCode(), ResponseCode.STRATEGY_RULE_WEIGHT_IS_NULL.getInfo());
        }
        Map<String, List<Integer>> ruleWeightValues = strategyRuleEntity.getRuleWeightValues();
        for (String key : ruleWeightValues.keySet()) {
            List<Integer> values = ruleWeightValues.get(key);
            ArrayList<StrategyAwardEntity> strategyAwardEntitiesTemp = new ArrayList<>(strategyAwardEntities);
            strategyAwardEntitiesTemp.removeIf(entity -> !values.contains(entity.getAwardId()));
            assembleLotteryStrategy(String.valueOf(strategyId).concat("_").concat(key), strategyAwardEntitiesTemp);
        }
        return true;
    }

    public void assembleLotteryStrategy(String key, List<StrategyAwardEntity> strategyAwardEntities) {
        if (strategyAwardEntities == null || strategyAwardEntities.isEmpty()) return;
        //2.获取最小概率值
        BigDecimal minRate = strategyAwardEntities.stream()
                .map(StrategyAwardEntity::getAwardRate)
                .min(BigDecimal::compareTo)// 找到最小的中奖率
                .orElse(BigDecimal.ZERO);// 如果没有找到最小的中奖率，则返回 BigDecimal.ZERO

        //3.获取概率总和
        BigDecimal totalAwardRate = strategyAwardEntities.stream()
                .map(StrategyAwardEntity::getAwardRate)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        //4.求概率范围，比如，若最小概率为0.01， 那么 1 / 0.01 = 100，则总范围100，每个概率占部分范围，0.01概率的占一分
        BigDecimal range = totalAwardRate.divide(minRate, 0, RoundingMode.CEILING);
        ArrayList<Integer> strategyAwardSearchRateTables = new ArrayList<>(range.intValue());
        for (StrategyAwardEntity strategyAwardEntity : strategyAwardEntities) {
            BigDecimal awardRate = strategyAwardEntity.getAwardRate();
            Integer awardId = strategyAwardEntity.getAwardId();
            // 计算出每个概率值需要存放到查找表的数量，循环填充
            for (int i = 0; i < range.multiply(awardRate).setScale(0, RoundingMode.CEILING).intValue(); i++) {
                strategyAwardSearchRateTables.add(awardId);
            }
        }
        //5.对存储的奖品进行乱序操作
        Collections.shuffle(strategyAwardSearchRateTables);

        //6.生成出Map集合，key值，对应的就是后续的概率值。通过概率来获得对应的奖品ID
        Map<Integer, Integer> shuffleStrategyAwardSearchRateTable = new LinkedHashMap<>();
        for (int i = 0; i < strategyAwardSearchRateTables.size(); i++) {
            shuffleStrategyAwardSearchRateTable.put(i, strategyAwardSearchRateTables.get(i));
        }

        //7.存放到 Redis
        repository.storeStrategyAwardSearchRateTable(key, shuffleStrategyAwardSearchRateTable.size(), shuffleStrategyAwardSearchRateTable);

    }

    @Override
    public Integer getRandomAwardId(Long strategyId) {
        // 分布式部署下，不一定为当前应用做的策略装配。也就是值不一定会保存到本应用，而是分布式应用，所以需要从 Redis 中获取。
        int rateRange = repository.getRateRange(strategyId);
        // 通过生成的随机值，获取概率值奖品查找表的结果
        return repository.getStrategyAwardAssemble(String.valueOf(strategyId), new SecureRandom().nextInt(rateRange));
    }


    @Override
    public Integer getRandomAwardId(Long strategyId, String ruleWeightValue) {
        String key = String.valueOf(strategyId).concat("_").concat(ruleWeightValue);
        // 分布式部署下，不一定为当前应用做的策略装配。也就是值不一定会保存到本应用，而是分布式应用，所以需要从 Redis 中获取。
        int rateRange = repository.getRateRange(key);
        // 通过生成的随机值，获取概率值奖品查找表的结果
        return repository.getStrategyAwardAssemble(key, new SecureRandom().nextInt(rateRange));
    }

}
