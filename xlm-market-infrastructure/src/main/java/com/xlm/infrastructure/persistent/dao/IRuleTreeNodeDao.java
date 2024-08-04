package com.xlm.infrastructure.persistent.dao;

import com.xlm.infrastructure.persistent.po.RuleTreeNode;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author xlm
 * 2024/7/22 下午7:32
 */
@Mapper
public interface IRuleTreeNodeDao {

    List<RuleTreeNode> queryRuleTreeNodeListByTreeId(String treeId);

    List<RuleTreeNode> queryRuleLocks(String[] treeIds);
}
