package com.xlm.trigger.api.dto;

import lombok.Data;

/**
 * @author xlm
 * 2024/8/3 下午7:52
 * 活动抽奖请求
 */
@Data
public class ActivityDrawRequestDTO {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 活动ID
     */
    private Long activityId;

}
