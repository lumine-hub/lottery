package com.xlm.infrastructure.persistent.dao;

import com.xlm.infrastructure.persistent.po.RuleTree;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author xlm
 * 2024/7/22 下午7:32
 */
@Mapper
public interface IRuleTreeDao {

    RuleTree queryRuleTreeByTreeId(String treeId);

}
