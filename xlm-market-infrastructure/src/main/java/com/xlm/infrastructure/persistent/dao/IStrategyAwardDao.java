package com.xlm.infrastructure.persistent.dao;

import com.xlm.infrastructure.persistent.po.StrategyAward;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xlm
 * 2024/7/16 下午7:23
 */
@Mapper
public interface IStrategyAwardDao {
    List<StrategyAward> queryAwardList();

    List<StrategyAward> queryStrategyAwardListByStrategyId(Long strategyId);

    String queryStrategyAwardRuleModels(@Param("strategyId") Long strategyId,@Param("awardId") Integer awardId);

    void updateStrategyAwardStock(StrategyAward strategyAward);

    StrategyAward queryStrategyAward(StrategyAward strategyAwardReq);

}
