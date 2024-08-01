package com.xlm.domain.award.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xlm
 * 2024/8/1 下午2:19
 */
@Getter
@AllArgsConstructor
public enum TaskStateVO {

    create("create", "创建"),
    complete("complete", "发送完成"),
    fail("fail", "发送失败"),
    ;

    private final String code;
    private final String desc;

}