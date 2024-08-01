package com.xlm.domain.task.service;

import java.util.List;
import com.xlm.domain.task.model.entity.TaskEntity;
/**
 * @author xlm
 * 2024/8/1 下午3:05
 */
public interface ITaskService {

    /**
     * 查询发送MQ失败和超时1分钟未发送的MQ
     *
     * @return 未发送的任务消息列表10条
     */
    List<TaskEntity> queryNoSendMessageTaskList();

    void sendMessage(TaskEntity taskEntity);

    void updateTaskSendMessageCompleted(String userId, String messageId);

    void updateTaskSendMessageFail(String userId, String messageId);

}