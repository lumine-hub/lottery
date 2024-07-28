package com.xlm.domain.strategy.model.vo;

import com.xlm.types.common.Constants;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xlm
 * 2024/7/20 下午2:09
 * 存放某一个award的rule，比如抽奖n次后才解锁
 */
@Data
@Builder
public class StrategyAwardRuleModelVO {
    private String ruleModels;
}
