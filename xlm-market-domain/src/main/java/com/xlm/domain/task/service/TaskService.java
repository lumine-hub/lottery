package com.xlm.domain.task.service;

import com.xlm.domain.task.model.entity.TaskEntity;
import com.xlm.domain.task.repository.ITaskRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xlm
 * 2024/8/1 下午3:05
 */
@Service
public class TaskService implements ITaskService {

    @Resource
    private ITaskRepository taskRepository;

    @Override
    public List<TaskEntity> queryNoSendMessageTaskList() {
        return taskRepository.queryNoSendMessageTaskList();
    }

    @Override
    public void sendMessage(TaskEntity taskEntity) {
        taskRepository.sendMessage(taskEntity);
    }

    @Override
    public void updateTaskSendMessageCompleted(String userId, String messageId) {
        taskRepository.updateTaskSendMessageCompleted(userId, messageId);
    }

    @Override
    public void updateTaskSendMessageFail(String userId, String messageId) {
        taskRepository.updateTaskSendMessageFail(userId, messageId);
    }

}
