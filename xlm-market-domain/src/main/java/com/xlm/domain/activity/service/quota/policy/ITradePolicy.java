package com.xlm.domain.activity.service.quota.policy;

import com.xlm.domain.activity.model.aggregate.CreateQuotaOrderAggregate;

/**
 * @author xlm
 * 2024/8/12 上午11:26
 */
public interface ITradePolicy {

    void trade(CreateQuotaOrderAggregate createQuotaOrderAggregate);

}