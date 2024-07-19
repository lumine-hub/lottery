package com.xlm.domain.service.rule;

import com.xlm.domain.model.entity.RuleActionEntity;
import com.xlm.domain.model.entity.RuleMatterEntity;

/**
 * @author xlm
 * 2024/7/19 下午5:47
 * 抽奖规则过滤接口
 */
public interface ILogicFilter<T extends RuleActionEntity.RaffleEntity> {

    RuleActionEntity<T> filter(RuleMatterEntity ruleMatterEntity);

}