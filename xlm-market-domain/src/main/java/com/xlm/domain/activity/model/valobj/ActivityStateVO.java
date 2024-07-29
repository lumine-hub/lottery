package com.xlm.domain.activity.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xlm
 * 2024/7/28 下午3:30
 */
@Getter
@AllArgsConstructor
public enum ActivityStateVO {

    create("create", "创建"),
    open("open", "开启"),
    close("close", "关闭"),
    ;

    private final String code;
    private final String desc;

}
