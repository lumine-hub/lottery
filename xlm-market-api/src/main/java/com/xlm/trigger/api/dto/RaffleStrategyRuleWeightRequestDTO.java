package com.xlm.trigger.api.dto;

import lombok.Data;

/**
 * @author xlm
 * 2024/8/8 下午5:20
 */
@Data
public class RaffleStrategyRuleWeightRequestDTO {

    // 用户ID
    private String userId;
    // 抽奖活动ID
    private Long activityId;

}