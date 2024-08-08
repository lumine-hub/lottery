package com.xlm.domain.rebate.repository;

import com.xlm.domain.rebate.model.aggregate.BehaviorRebateAggregate;
import com.xlm.domain.rebate.model.entity.BehaviorEntity;
import com.xlm.domain.rebate.model.entity.BehaviorRebateOrderEntity;
import com.xlm.domain.rebate.model.vo.BehaviorTypeVO;
import com.xlm.domain.rebate.model.vo.DailyBehaviorRebateVO;

import java.util.List;

/**
 * @author xlm
 * 2024/8/6 下午9:32
 */
public interface IBehaviorRebateRepository {

    List<DailyBehaviorRebateVO> queryDailyBehaviorRebateConfig(BehaviorTypeVO behaviorTypeVO);

    void saveUserRebateRecord(String userId, List<BehaviorRebateAggregate> behaviorRebateAggregates);

    List<BehaviorRebateOrderEntity> queryOrderByOutBusinessNo(String userId, String outBusinessNo);
}
