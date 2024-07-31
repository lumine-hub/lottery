package com.xlm.test.domain.activity;

import com.xlm.domain.activity.model.entity.SkuRechargeEntity;
import com.xlm.domain.activity.service.IRaffleActivityAccountQuotaService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author xlm
 * 2024/7/28 下午3:54
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RaffleOrderTest_15 {

    @Resource
    private IRaffleActivityAccountQuotaService raffleOrder;

    @Test
    public void test_createSkuRechargeOrder() {
        SkuRechargeEntity skuRechargeEntity = new SkuRechargeEntity();
        skuRechargeEntity.setUserId("xlm");
        skuRechargeEntity.setSku(9011L);
        // outBusinessNo 作为幂等仿重使用，同一个业务单号2次使用会抛出索引冲突 Duplicate entry '700091009111' for key 'uq_out_business_no' 确保唯一性。
        skuRechargeEntity.setOutBusinessNo("700091009421");
        String orderId = raffleOrder.createOrder(skuRechargeEntity);
        log.info("测试结果：{}", orderId);
    }

}