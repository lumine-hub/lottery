package com.xlm.domain.activity.repository;

import com.xlm.domain.activity.model.entity.ActivityCountEntity;
import com.xlm.domain.activity.model.entity.ActivityEntity;
import com.xlm.domain.activity.model.entity.ActivitySkuEntity;

import java.lang.reflect.InvocationTargetException;

/**
 * @author xlm
 * 2024/7/28 下午3:31
 */
public interface IActivityRepository {

    ActivitySkuEntity queryActivitySku(Long sku) ;

    ActivityEntity queryRaffleActivityByActivityId(Long activityId);

    ActivityCountEntity queryRaffleActivityCountByActivityCountId(Long activityCountId);
}
