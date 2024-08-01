package com.xlm.infrastructure.persistent.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
import org.apache.ibatis.annotations.Mapper;
import com.xlm.infrastructure.persistent.po.Task;

import java.util.List;

/**
 * @author xlm
 * 2024/7/30 下午3:31
 * 任务表，发送mq
 */
@Mapper
public interface ITaskDao {

    void insert(Task task);

    @DBRouter
    void updateTaskSendMessageCompleted(Task task);

    @DBRouter
    void updateTaskSendMessageFail(Task task);

    List<Task> queryNoSendMessageTaskList();

}
