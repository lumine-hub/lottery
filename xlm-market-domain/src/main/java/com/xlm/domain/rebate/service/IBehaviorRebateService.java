package com.xlm.domain.rebate.service;

import com.xlm.domain.rebate.model.entity.BehaviorEntity;

import java.util.List;

/**
 * @author xlm
 * 2024/8/6 下午9:33
 */
public interface IBehaviorRebateService {

    List<String> createOrder(BehaviorEntity behaviorEntity);

}
