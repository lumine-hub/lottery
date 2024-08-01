package com.xlm.infrastructure.persistent.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouterStrategy;
import com.xlm.infrastructure.persistent.po.UserAwardRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xlm
 * 2024/7/30 下午3:32
 * 用户中奖记录表
 */
@Mapper
@DBRouterStrategy(splitTable = true)
public interface IUserAwardRecordDao {

    void insert(UserAwardRecord userAwardRecord);

}
