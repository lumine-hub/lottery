package com.xlm.domain.award.service;

import com.xlm.domain.award.event.SendAwardMessageEvent;
import com.xlm.domain.award.model.aggregate.UserAwardRecordAggregate;
import com.xlm.domain.award.model.entity.TaskEntity;
import com.xlm.domain.award.model.entity.UserAwardRecordEntity;
import com.xlm.domain.award.model.valobj.TaskStateVO;
import com.xlm.domain.award.repository.IAwardRepository;
import com.xlm.types.event.BaseEvent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author xlm
 * 2024/8/1 下午2:31
 */
@Service
public class AwardService implements IAwardService{
    @Resource
    private IAwardRepository awardRepository;
    @Resource
    private SendAwardMessageEvent sendAwardMessageEvent;

    @Override
    public void saveUserAwardRecord(UserAwardRecordEntity userAwardRecordEntity) {
        // 1.构建消息对象
        SendAwardMessageEvent.SendAwardMessage sendAwardMessage = new SendAwardMessageEvent.SendAwardMessage();
        sendAwardMessage.setUserId(userAwardRecordEntity.getUserId());
        sendAwardMessage.setAwardId(userAwardRecordEntity.getAwardId());
        sendAwardMessage.setAwardTitle(userAwardRecordEntity.getAwardTitle());

        BaseEvent.EventMessage<SendAwardMessageEvent.SendAwardMessage> sendAwardMessageEventMessage = sendAwardMessageEvent.buildEventMessage(sendAwardMessage);

        // 2.构造task
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setUserId(userAwardRecordEntity.getUserId());
        taskEntity.setTopic(sendAwardMessageEvent.topic());
        taskEntity.setMessageId(sendAwardMessageEventMessage.getId());
        taskEntity.setMessage(sendAwardMessageEventMessage);
        taskEntity.setState(TaskStateVO.create);

        // 3.构建聚合对象
        UserAwardRecordAggregate userAwardRecordAggregate = UserAwardRecordAggregate.builder()
                .taskEntity(taskEntity)
                .userAwardRecordEntity(userAwardRecordEntity)
                .build();

        // 4.存储聚合对象 - 一个事务下，用户的中奖记录
        awardRepository.saveUserAwardRecord(userAwardRecordAggregate);


    }
}
