package com.xlm.infrastructure.persistent.dao;

import com.xlm.infrastructure.persistent.po.RaffleActivity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xlm
 * 2024/7/27 下午4:13
 */
@Mapper
public interface IRaffleActivityDao {

    RaffleActivity queryRaffleActivityByActivityId(Long activityId);

}