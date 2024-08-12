package com.xlm.domain.activity.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xlm
 * 2024/7/28 下午3:30
 */
@Getter
@AllArgsConstructor
public enum OrderStateVO {

    wait_pay("wait_pay","待支付"),
    completed("completed", "完成"),
    ;

    private final String code;
    private final String desc;

}