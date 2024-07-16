package com.xlm.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

/**
 * @author xlm
 * 2024/7/16 下午6:46
 * 抽奖策略
 */
@Data
public class Strategy {
    /**
     * 自增ID
     */
    private long id;
    /**
     * 抽奖策略ID
     */
    private long strategyId;
    /**
     * 抽奖策略描述
     */
    private String strategyDesc;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

}
