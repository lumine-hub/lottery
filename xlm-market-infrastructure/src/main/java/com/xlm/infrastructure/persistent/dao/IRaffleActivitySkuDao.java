package com.xlm.infrastructure.persistent.dao;

import com.xlm.infrastructure.persistent.po.RaffleActivitySku;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface IRaffleActivitySkuDao {

    RaffleActivitySku queryActivitySku(Long sku);

}
