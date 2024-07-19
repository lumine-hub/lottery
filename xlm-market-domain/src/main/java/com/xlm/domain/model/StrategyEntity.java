package com.xlm.domain.model;

import com.xlm.types.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * @author xlm
 * 2024/7/18 下午1:01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StrategyEntity {

    /**
     * 抽奖策略ID
     */
    private long strategyId;

    /** 抽奖规则模型 */
    private String ruleModels;
    /**
     * 抽奖策略描述
     */
    private String strategyDesc;

    public String[] ruleModels() {
        if (StringUtils.isBlank(ruleModels)) return null;
        return ruleModels.split(Constants.SPLIT);
    }

    public String getRuleWeight() {
        String[] ruleModels = this.ruleModels();
        for (String ruleModel : ruleModels) {
            if ("rule_weight".equals(ruleModel)) return ruleModel;
        }
        return null;
    }
}
