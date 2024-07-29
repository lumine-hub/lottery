package com.xlm.domain.activity.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xlm
 * 2024/7/29 下午3:46
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivitySkuStockKeyVO {

    /** 商品sku */
    private Long sku;
    /** 活动ID */
    private Long activityId;

}
