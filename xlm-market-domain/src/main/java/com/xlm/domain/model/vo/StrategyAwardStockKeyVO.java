package com.xlm.domain.model.vo;

import lombok.*;

/**
 * @author xlm
 * 2024/7/23 下午2:43
 * 策略奖品库存Key标识值对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StrategyAwardStockKeyVO {

    // 策略ID
    private Long strategyId;
    // 奖品ID
    private Integer awardId;

}

