package com.xlm.domain.credit.repository;


import com.xlm.domain.credit.model.aggregate.TradeAggregate;
import com.xlm.domain.credit.model.entity.CreditAccountEntity;

/**
 * @author xlm
 * 2024/8/10 下午2:25
 */
public interface ICreditRepository {

    void saveUserCreditTradeOrder(TradeAggregate tradeAggregate);

    CreditAccountEntity queryUserCreditAccount(String userId);
}
