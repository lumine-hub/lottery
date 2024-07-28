package com.xlm.test.domain.strategy;


import com.xlm.domain.strategy.service.armory.StrategyArmoryDispatch;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author xlm
 * 2024/7/17 下午2:35
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyArmoryDispatchTest {

    @Resource
    private StrategyArmoryDispatch strategyArmoryDispatch;


    @Test
    public void assembleLotteryStrategyTest() {
        System.out.println(strategyArmoryDispatch.assembleLotteryStrategy(100002L));
    }

    @Test
    public void getRandomAwardIdTest() {
        for (int i = 0; i < 10; i++) {
            Integer randomAwardId = strategyArmoryDispatch.getRandomAwardId(100002L);
            System.out.println(randomAwardId);
        }
    }

    /**
     * 策略ID；100001L、100002L 装配的时候创建策略表写入到 Redis Map 中
     */
    @Before
    public void test_strategyArmory() {
        boolean success = strategyArmoryDispatch.assembleLotteryStrategy(100001L);
        log.info("测试结果：{}", success);
    }

    /**
     * 从装配的策略中随机获取奖品ID值
     */
    @Test
    public void test_getRandomAwardId() {
        Integer awardId = strategyArmoryDispatch.getRandomAwardId(100001L);
        log.info("测试结果：{} - 奖品ID值", awardId);

    }

    /**
     * 根据策略ID+权重值，从装配的策略中随机获取奖品ID值
     */
    @Test
    public void test_getRandomAwardId_ruleWeightValue() {
        log.info("测试结果：{} - 4000 策略配置", strategyArmoryDispatch.getRandomAwardId(100001L, "4000:102,103,104,105"));
        log.info("测试结果：{} - 5000 策略配置", strategyArmoryDispatch.getRandomAwardId(100001L, "5000:102,103,104,105,106,107"));
        log.info("测试结果：{} - 6000 策略配置", strategyArmoryDispatch.getRandomAwardId(100001L, "6000:102,103,104,105,106,107,108,109"));
    }
}