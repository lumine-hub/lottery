package com.xlm.trigger.api.dto;

import lombok.Data;

/**
 * @author xlm
 * 2024/8/8 下午4:20
 */
@Data
public class UserActivityAccountRequestDTO {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 活动ID
     */
    private Long activityId;

}
