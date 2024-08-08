package com.xlm.domain.rebate.service;

import com.xlm.domain.rebate.model.entity.BehaviorEntity;
import com.xlm.domain.rebate.model.entity.BehaviorRebateOrderEntity;

import java.util.List;

/**
 * @author xlm
 * 2024/8/6 下午9:33
 */
public interface IBehaviorRebateService {

    List<String> createOrder(BehaviorEntity behaviorEntity);

    /**
     * 根据外部单号查询订单
     *
     * @param userId        用户ID
     * @param outBusinessNo 业务ID；签到则是日期字符串，支付则是外部的业务ID
     * @return 返利订单实体
     */
    List<BehaviorRebateOrderEntity> queryOrderByOutBusinessNo(String userId, String outBusinessNo);
}
