package com.xlm.domain.strategy.model.entity;

import com.xlm.domain.strategy.model.vo.RuleLogicCheckTypeVO;
import lombok.*;

/**
 * @author xlm
 * 2024/7/19 下午5:48
 * 规则动作实体: 规则过滤后返回的action
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleActionEntity<T extends RuleActionEntity.RaffleEntity> {

    private String code = RuleLogicCheckTypeVO.ALLOW.getCode();
    private String info = RuleLogicCheckTypeVO.ALLOW.getInfo();
    private String ruleModel;
    private T data;

    static public class RaffleEntity {

    }

    // 抽奖之前
    @EqualsAndHashCode(callSuper = true)
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    static public class RaffleBeforeEntity extends RaffleEntity {
        /**
         * 策略ID
         */
        private Long strategyId;

        /**
         * 权重值Key；用于抽奖时可以选择权重抽奖。
         */
        private String ruleWeightValueKey;

        /**
         * 奖品ID；
         */
        private Integer awardId;
    }

    // 抽奖之中
    static public class RaffleCenterEntity extends RaffleEntity {
        private Integer awardId;
    }

    // 抽奖之后
    static public class RaffleAfterEntity extends RaffleEntity {

    }

}