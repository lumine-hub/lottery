package com.xlm.infrastructure.persistent.dao;

import com.xlm.infrastructure.persistent.po.RuleTreeNodeLine;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author xlm
 * 2024/7/22 下午7:32
 */
@Mapper
public interface IRuleTreeNodeLineDao {

    List<RuleTreeNodeLine> queryRuleTreeNodeLineListByTreeId(String treeId);

}