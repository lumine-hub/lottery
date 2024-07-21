package com.xlm.domain.service.rule.chain.impl;

import com.xlm.domain.service.armory.IStrategyDispatch;
import com.xlm.domain.service.rule.chain.AbstractLogicChain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author xlm
 * 2024/7/21 下午3:39
 * 【默认抽奖规则】 在经过了前面的黑名单等过滤后，责任链处理后最后的兜底抽奖规则。
 */
@Slf4j
@Component("default")
public class DefaultLogicChain extends AbstractLogicChain {

    @Resource
    private IStrategyDispatch strategyDispatch;
    @Override
    public Integer logic(String userId,Long strategyId) {
        Integer awardId = strategyDispatch.getRandomAwardId(strategyId);
        log.info("【默认抽奖规则】userId:{},strategyId:{},awardId:{}", userId, strategyId, awardId);
        return awardId;
    }

    @Override
    protected String ruleModel() {
        return "default";
    }
}
