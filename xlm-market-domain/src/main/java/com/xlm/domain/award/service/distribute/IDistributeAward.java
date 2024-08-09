package com.xlm.domain.award.service.distribute;

import com.xlm.domain.award.model.entity.DistributeAwardEntity;

/**
 * @author xlm
 * 2024/8/9 下午2:22
 * 分发奖品接口
 */
public interface IDistributeAward {
    void giveOutPrizes(DistributeAwardEntity distributeAwardEntity);

}
