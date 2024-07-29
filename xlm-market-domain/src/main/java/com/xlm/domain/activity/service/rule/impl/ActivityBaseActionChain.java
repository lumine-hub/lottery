package com.xlm.domain.activity.service.rule.impl;

import com.xlm.domain.activity.model.entity.ActivityCountEntity;
import com.xlm.domain.activity.model.entity.ActivityEntity;
import com.xlm.domain.activity.model.entity.ActivitySkuEntity;
import com.xlm.domain.activity.model.valobj.ActivityStateVO;
import com.xlm.domain.activity.service.rule.AbstractActionChain;
import com.xlm.types.enums.ResponseCode;
import com.xlm.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author xlm
 * 2024/7/28 下午9:51
 * 活动规则基础校验（时间、状态、库存）
 */
@Slf4j
@Component("activity_base_action")
public class ActivityBaseActionChain extends AbstractActionChain {
    @Override
    public boolean action(ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity) {
        log.info("活动责任链-基础信息【有效期、状态、库存(sku)】校验开始。sku:{} activityId:{}", activitySkuEntity.getSku(), activityEntity.getActivityId());
        // 检查活动状态
        if (!ActivityStateVO.open.equals(activityEntity.getState())) {
            throw new AppException(ResponseCode.ACTIVITY_STATE_ERROR.getCode(), ResponseCode.ACTIVITY_STATE_ERROR.getInfo());
        }
        // 检查活动时间
        Date beginDateTime = activityEntity.getBeginDateTime();
        Date endDateTime = activityEntity.getEndDateTime();
        if (beginDateTime.after(new Date()) || endDateTime.before(new Date())) {
            throw new AppException(ResponseCode.ACTIVITY_DATE_ERROR.getCode(), ResponseCode.ACTIVITY_DATE_ERROR.getInfo());
        }
        // 校验；活动sku库存 「剩余库存从缓存获取的」
        if (activitySkuEntity.getStockCountSurplus() <= 0) {
            throw new AppException(ResponseCode.ACTIVITY_SKU_STOCK_ERROR.getCode(), ResponseCode.ACTIVITY_SKU_STOCK_ERROR.getInfo());
        }
        return next().action(activitySkuEntity, activityEntity, activityCountEntity);
    }
}
