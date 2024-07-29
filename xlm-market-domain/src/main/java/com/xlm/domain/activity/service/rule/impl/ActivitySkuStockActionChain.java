package com.xlm.domain.activity.service.rule.impl;

import com.xlm.domain.activity.model.entity.ActivityCountEntity;
import com.xlm.domain.activity.model.entity.ActivityEntity;
import com.xlm.domain.activity.model.entity.ActivitySkuEntity;
import com.xlm.domain.activity.model.valobj.ActivitySkuStockKeyVO;
import com.xlm.domain.activity.repository.IActivityRepository;
import com.xlm.domain.activity.service.armory.IActivityDispatch;
import com.xlm.domain.activity.service.rule.AbstractActionChain;
import com.xlm.types.enums.ResponseCode;
import com.xlm.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author xlm
 * 2024/7/28 下午9:54
 * 对库存进行处理,扣减库存
 */
@Slf4j
@Component("activity_sku_stock_action")
public class ActivitySkuStockActionChain extends AbstractActionChain {

    @Resource
    private IActivityDispatch activityDispatch;
    @Resource
    private IActivityRepository activityRepository;

    @Override
    public boolean action(ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity) {
        log.info("活动责任链-商品库存处理【有效期、状态、库存(sku)】开始。sku:{} activityId:{}", activitySkuEntity.getSku(), activityEntity.getActivityId());
        // 1. 扣减库存
        boolean status = activityDispatch.subtractionActivitySkuStock(activitySkuEntity.getSku(), activityEntity.getEndDateTime());
        if (!status) {
            throw new AppException(ResponseCode.ACTIVITY_SKU_STOCK_ERROR.getCode(), ResponseCode.ACTIVITY_SKU_STOCK_ERROR.getInfo());
        }
        log.info("活动责任链-商品库存处理【有效期、状态、库存(sku)】成功。sku:{} activityId:{}", activitySkuEntity.getSku(), activityEntity.getActivityId());

        // 库存扣减成功，写入延迟队列，延迟消费更新库存记录
        activityRepository.activitySkuStockConsumeSendQueue(ActivitySkuStockKeyVO.builder()
                .sku(activitySkuEntity.getSku())
                .activityId(activityEntity.getActivityId())
                .build());
        return true;
    }
}
