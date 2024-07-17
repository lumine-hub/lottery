package com.xlm.test.domain;


import com.xlm.domain.model.StrategyAwardEntity;
import com.xlm.domain.repository.IStrategyRepository;
import com.xlm.domain.service.armory.IStrategyArmory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xlm
 * 2024/7/17 下午2:35
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyArmoryTest {
    @Resource
    private IStrategyRepository strategyRepository;

    @Resource
    private IStrategyArmory strategyArmory;

//    @Test
//    public void queryStrategyAwardList() {
//        List<StrategyAwardEntity> strategyAwardEntities = strategyRepository.queryStrategyAwardList(100001l);
//        for (StrategyAwardEntity strategyAwardEntity : strategyAwardEntities) {
//            System.out.println(strategyAwardEntity);
//            System.out.println("------------");
//        }
//    }

    @Test
    public void assembleLotteryStrategyTest() {
        System.out.println(strategyArmory.assembleLotteryStrategy(100002L));
    }

    @Test
    public void getRandomAwardIdTest() {
        for (int i = 0; i < 10; i++) {
            Integer randomAwardId = strategyArmory.getRandomAwardId(100002l);
            System.out.println(randomAwardId);
        }
    }
}