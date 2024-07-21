package com.xlm.test.domain;

import com.alibaba.fastjson.JSON;
import com.xlm.domain.model.entity.RaffleAwardEntity;
import com.xlm.domain.model.entity.RaffleFactorEntity;
import com.xlm.domain.service.IRaffleStrategy;
import com.xlm.domain.service.armory.IStrategyArmory;
import com.xlm.domain.service.rule.chain.ILogicChain;
import com.xlm.domain.service.rule.chain.factory.DefaultChainFactory;
import com.xlm.domain.service.rule.chain.impl.RuleWeightLogicChain;
import com.xlm.domain.service.rule.filter.impl.RuleWeightLogicFilter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.annotation.Resource;
/**
 * @author xlm
 * 2024/7/21 下午5:11
 * 责任链测试
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RaffleStrategyTest_7 {

    @Resource
    private IStrategyArmory strategyArmory;
    @Resource
    private RuleWeightLogicChain ruleWeightLogicChain;
    @Resource
    private DefaultChainFactory defaultChainFactory;

    @Before
    public void setUp() {
        // 策略装配 100001、100002、100003
        log.info("测试结果：{}", strategyArmory.assembleLotteryStrategy(100001L));
        log.info("测试结果：{}", strategyArmory.assembleLotteryStrategy(100002L));
        log.info("测试结果：{}", strategyArmory.assembleLotteryStrategy(100003L));
    }

    @Test
    public void test_LogicChain_rule_blacklist() {
        ILogicChain logicChain = defaultChainFactory.openLogicChain(100001L);
        Integer awardId = logicChain.logic("user001", 100001L);
        log.info("测试结果：{}", awardId);
    }

    @Test
    public void test_LogicChain_rule_weight() {
        // 通过反射 mock 规则中的值
        ReflectionTestUtils.setField(ruleWeightLogicChain, "userScore", 4900L);

        ILogicChain logicChain = defaultChainFactory.openLogicChain(100001L);
        Integer awardId = logicChain.logic("xlm", 100001L);
        log.info("测试结果：{}", awardId);
    }

    @Test
    public void test_LogicChain_rule_default() {
        ILogicChain logicChain = defaultChainFactory.openLogicChain(100001L);
        Integer awardId = logicChain.logic("xlm", 100001L);
        log.info("测试结果：{}", awardId);
    }

}