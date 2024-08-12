package com.xlm.domain.activity.service.quota.policy.impl;

import com.xlm.domain.activity.model.aggregate.CreateQuotaOrderAggregate;
import com.xlm.domain.activity.model.valobj.OrderStateVO;
import com.xlm.domain.activity.repository.IActivityRepository;
import com.xlm.domain.activity.service.quota.policy.ITradePolicy;
import org.springframework.stereotype.Service;

/**
 * @author xlm
 * 2024/8/12 上午11:27
 */
@Service("credit_pay_trade")
public class CreditPayTradePolicy implements ITradePolicy {

    private final IActivityRepository activityRepository;

    public CreditPayTradePolicy(IActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public void trade(CreateQuotaOrderAggregate createQuotaOrderAggregate) {
        createQuotaOrderAggregate.setOrderState(OrderStateVO.wait_pay);
        activityRepository.doSaveCreditPayOrder(createQuotaOrderAggregate);
    }

}