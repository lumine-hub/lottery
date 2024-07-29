package com.xlm.domain.activity.repository;

import com.xlm.domain.activity.model.aggregate.CreateOrderAggregate;
import com.xlm.domain.activity.model.entity.ActivityCountEntity;
import com.xlm.domain.activity.model.entity.ActivityEntity;
import com.xlm.domain.activity.model.entity.ActivitySkuEntity;
import com.xlm.domain.activity.model.valobj.ActivitySkuStockKeyVO;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

/**
 * @author xlm
 * 2024/7/28 下午3:31
 */
public interface IActivityRepository {

    ActivitySkuEntity queryActivitySku(Long sku) ;

    ActivityEntity queryRaffleActivityByActivityId(Long activityId);

    ActivityCountEntity queryRaffleActivityCountByActivityCountId(Long activityCountId);

    void doSaveOrder(CreateOrderAggregate createOrderAggregate);

    boolean subtractionActivitySkuStock(Long sku, String cacheKey, Date endDateTime);

    void activitySkuStockConsumeSendQueue(ActivitySkuStockKeyVO build);


    ActivitySkuStockKeyVO takeQueueValue();

    void clearQueueValue();

    void updateActivitySkuStock(Long sku);

    void clearActivitySkuStock(Long sku);

    void cacheActivitySkuStockCount(String cacheKey, Integer stockCount);
}
