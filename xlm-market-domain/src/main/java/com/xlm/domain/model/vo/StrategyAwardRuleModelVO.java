package com.xlm.domain.model.vo;

import com.xlm.domain.service.rule.filter.factory.DefaultLogicFactory;
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
    public String[] raffleCenterRuleModelList() {
        List<String> ruleModelList = new ArrayList<>();
        for (String ruleModel : ruleModels.split(Constants.SPLIT)) {
            if (DefaultLogicFactory.LogicModel.isCenter(ruleModel)) {
                ruleModelList.add(ruleModel);
            }
        }
        return ruleModelList.toArray(new String[ruleModelList.size()]);
    }

//    public String[] raffleAfterRuleModelList() {
//        List<String> ruleModelList = new ArrayList<>();
//        String[] ruleModelValues = ruleModels.split(Constants.SPLIT);
//        for (String ruleModelValue : ruleModelValues) {
//            if (DefaultLogicFactory.LogicModel.isAfter(ruleModelValue)) {
//                ruleModelList.add(ruleModelValue);
//            }
//        }
//        return ruleModelList.toArray(new String[0]);
//    }
}
