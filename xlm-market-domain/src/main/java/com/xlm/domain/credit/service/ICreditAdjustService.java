package com.xlm.domain.credit.service;


import com.xlm.domain.credit.model.entity.CreditAccountEntity;
import com.xlm.domain.credit.model.entity.TradeEntity;

/**
 * @author xlm
 * 2024/8/10 下午2:20
 */
public interface ICreditAdjustService {

    /**
     * 创建增加积分额度订单
     * @param tradeEntity 交易实体对象
     * @return 单号
     */
    String createOrder(TradeEntity tradeEntity);

    /**
     * 查询用户积分账户
     * @param userId 用户ID
     * @return 积分账户实体
     */
    CreditAccountEntity queryUserCreditAccount(String userId);
}
