package com.xlm.domain.activity.service.rule.impl;

import com.xlm.domain.activity.model.entity.ActivityCountEntity;
import com.xlm.domain.activity.model.entity.ActivityEntity;
import com.xlm.domain.activity.model.entity.ActivitySkuEntity;
import com.xlm.domain.activity.service.rule.AbstractActionChain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author xlm
 * 2024/7/28 下午9:51
 */
@Slf4j
@Component("activity_base_action")
public class ActivityBaseActionChain extends AbstractActionChain {
    @Override
    public boolean action(ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity) {
        return true;
    }
}
