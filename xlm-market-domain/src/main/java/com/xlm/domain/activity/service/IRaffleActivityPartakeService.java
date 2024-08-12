package com.xlm.domain.activity.service;

import com.xlm.domain.activity.model.entity.PartakeRaffleActivityEntity;
import com.xlm.domain.activity.model.entity.UserRaffleOrderEntity;

/**
 * @author xlm
 * 2024/7/31 下午9:09
 * 用户进行抽奖的接口，用户点击抽奖后的情况
 */
public interface IRaffleActivityPartakeService {
    /**
     * 创建抽奖单；用户参与抽奖活动，扣减活动账户（quota即抽奖次数raffle_activity_account）库存，
     * user_raffle_order表新增一条记录，返回用户抽奖订单实体对象
     * 产生抽奖单。如存在未被使用的抽奖单则直接返回已存在的抽奖单。
     *
     * @param partakeRaffleActivityEntity 参与抽奖活动实体对象
     * @return 用户抽奖订单实体对象
     */
    UserRaffleOrderEntity createOrder(PartakeRaffleActivityEntity partakeRaffleActivityEntity);
}
