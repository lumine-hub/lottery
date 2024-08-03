package com.xlm.domain.activity.service.armory;

/**
 * @author xlm
 * 2024/7/29 下午4:18
 * 活动预热装配
 */
public interface IActivityArmory {
    // 通过sku装配
    boolean assembleActivitySku(Long sku);

    // 通过活动id装配
    boolean assembleActivitySkuByActivityId(Long activityId);
}
