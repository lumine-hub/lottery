package com.xlm.infrastructure.persistent.repository;

import com.xlm.domain.model.entity.StrategyAwardEntity;
import com.xlm.domain.model.entity.StrategyEntity;
import com.xlm.domain.model.entity.StrategyRuleEntity;
import com.xlm.domain.model.vo.StrategyAwardRuleModelVO;
import com.xlm.domain.repository.IStrategyRepository;
import com.xlm.infrastructure.persistent.dao.IStrategyAwardDao;
import com.xlm.infrastructure.persistent.dao.IStrategyDao;
import com.xlm.infrastructure.persistent.dao.IStrategyRuleDao;
import com.xlm.infrastructure.persistent.po.Strategy;
import com.xlm.infrastructure.persistent.po.StrategyAward;
import com.xlm.infrastructure.persistent.po.StrategyRule;
import com.xlm.infrastructure.persistent.redis.IRedisService;
import com.xlm.types.common.Constants;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xlm
 * 2024/7/17 下午2:15
 * 策略服务仓储实现
 */
@Repository
public class StrategyRepository implements IStrategyRepository {

    @Resource
    private IRedisService redisService;

    @Resource
    private IStrategyAwardDao strategyAwardDao;

    @Resource
    private IStrategyDao strategyDao;

    @Resource
    private IStrategyRuleDao strategyRuleDao;


    @Override
    public List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId) {
        // 1.首先从缓存获取数据
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_KEY + strategyId;
        List<StrategyAwardEntity> strategyAwardEntities = redisService.getValue(cacheKey);
        if (strategyAwardEntities != null && !strategyAwardEntities.isEmpty()) {
            return strategyAwardEntities;
        }
        // 2.再次选择从mysql中获取数据
        List<StrategyAward> strategyAwards = strategyAwardDao.queryStrategyAwardListByStrategyId(strategyId);
        strategyAwardEntities = new ArrayList<>(strategyAwards.size()); // 避免空指针异常
        for (StrategyAward strategyAward : strategyAwards) {
            StrategyAwardEntity strategyAwardEntity = new StrategyAwardEntity();
            BeanUtils.copyProperties(strategyAward, strategyAwardEntity);
            strategyAwardEntities.add(strategyAwardEntity);
        }
        // 3.存到redis
        redisService.setValue(cacheKey, strategyAwardEntities);

        return strategyAwardEntities;
    }

    @Override
    public void storeStrategyAwardSearchRateTable(String strategyId, Integer rateRange, Map<Integer, Integer> strategyAwardSearchRateTable) {
        // 1. 存储抽奖策略范围值，如10000，用于生成1000以内的随机数
        redisService.setValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY + strategyId, rateRange);
        // 2. 存储概率查找表
        Map<Integer, Integer> cacheRateTable = redisService.getMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY + strategyId);
        cacheRateTable.putAll(strategyAwardSearchRateTable);
    }

    @Override
    public Integer getStrategyAwardAssemble(Long strategyId, Integer rateKey) {
        Integer awardId = redisService.getFromMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY + strategyId, rateKey);
        return awardId;
    }

    @Override
    public Integer getStrategyAwardAssemble(String key, Integer rateKey) {
        return redisService.getFromMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY + key, rateKey);
    }

    @Override
    public int getRateRange(Long strategyId) {
        return getRateRange(String.valueOf(strategyId));
    }

    @Override
    public int getRateRange(String key) {
        return redisService.getValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY + key);
    }

    @Override
    public StrategyEntity queryStrategyEntityByStrategyId(Long strategyId) {
        // 1.首先从缓存获取数据
        String cacheKey = Constants.RedisKey.STRATEGY_KEY + strategyId;
        StrategyEntity strategyEntity = redisService.getValue(cacheKey);
        if (null != strategyEntity) return strategyEntity;
        Strategy strategy = strategyDao.queryStrategyByStrategyId(strategyId);
        strategyEntity = StrategyEntity.builder()
                .strategyId(strategy.getStrategyId())
                .strategyDesc(strategy.getStrategyDesc())
                .ruleModels(strategy.getRuleModels())
                .build();
        redisService.setValue(cacheKey, strategyEntity);
        return strategyEntity;
    }

    @Override
    public StrategyRuleEntity queryStrategyRule(Long strategyId, String ruleModel) {
        StrategyRule strategyRuleReq = new StrategyRule();
        strategyRuleReq.setStrategyId(strategyId);
        strategyRuleReq.setRuleModel(ruleModel);
        StrategyRule strategyRuleRes = strategyRuleDao.queryStrategyRule(strategyRuleReq);
        return StrategyRuleEntity.builder()
                .strategyId(strategyRuleRes.getStrategyId())
                .awardId(strategyRuleRes.getAwardId())
                .ruleType(strategyRuleRes.getRuleType())
                .ruleModel(strategyRuleRes.getRuleModel())
                .ruleValue(strategyRuleRes.getRuleValue())
                .ruleDesc(strategyRuleRes.getRuleDesc())
                .build();
    }

    @Override
    public String queryStrategyRuleValue(Long strategyId, Integer awardId, String ruleModel) {
        StrategyRule strategyRule = new StrategyRule();
        strategyRule.setStrategyId(strategyId);
        strategyRule.setAwardId(awardId);
        strategyRule.setRuleModel(ruleModel);
        return strategyRuleDao.queryStrategyRuleValue(strategyRule);
    }
    @Override
    public String queryStrategyRuleValue(Long strategyId, String ruleModel) {
        return this.queryStrategyRuleValue(strategyId, null ,ruleModel);
    }

    @Override
    public StrategyAwardRuleModelVO queryStrategyAwardRuleModelVO(Long strategyId, Integer awardId) {
        String s = strategyAwardDao.queryStrategyAwardRuleModels(strategyId, awardId);
        return StrategyAwardRuleModelVO.builder().ruleModels(s).build();
    }

}
