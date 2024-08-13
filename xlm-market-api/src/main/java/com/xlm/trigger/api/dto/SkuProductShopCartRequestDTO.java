package com.xlm.trigger.api.dto;

import lombok.Data;

/**
 * @author xlm
 * 2024/8/12 下午2:26
 * 商品购物车请求对象
 */
@Data
public class SkuProductShopCartRequestDTO {

    /**
     * 用户ID
     */
    private String userId;
    /**
     * sku 商品
     */
    private Long sku;

}