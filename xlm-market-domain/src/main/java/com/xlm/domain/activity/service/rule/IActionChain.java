package com.xlm.domain.activity.service.rule;

import com.xlm.domain.activity.model.entity.ActivityCountEntity;
import com.xlm.domain.activity.model.entity.ActivityEntity;
import com.xlm.domain.activity.model.entity.ActivitySkuEntity;

/**
 * @author xlm
 * 2024/7/28 下午9:47
 * 规则校验，判断当前的信息是否可以开始下订单的流程了。
 */
public interface IActionChain extends IActionChainArmory{

    boolean action(ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity);
}
