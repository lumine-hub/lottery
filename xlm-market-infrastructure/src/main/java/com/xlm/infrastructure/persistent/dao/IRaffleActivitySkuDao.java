package com.xlm.infrastructure.persistent.dao;

import com.xlm.infrastructure.persistent.po.RaffleActivitySku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface IRaffleActivitySkuDao {

    RaffleActivitySku queryActivitySku(Long sku);

    void updateActivitySkuStock(Long sku);

    void clearActivitySkuStock(Long sku);

    List<RaffleActivitySku> queryActivitySkuListByActivityId(Long activityId);
}
