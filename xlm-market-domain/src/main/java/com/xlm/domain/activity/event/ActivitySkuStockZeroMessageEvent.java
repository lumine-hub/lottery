package com.xlm.domain.activity.event;

import com.xlm.types.event.BaseEvent;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;
/**
 * @author xlm
 * 2024/7/29 下午4:37
 * 活动库存已经为空的消息
 */
@Component
public class ActivitySkuStockZeroMessageEvent extends BaseEvent<Long> {

    @Value("${spring.rabbitmq.topic.activity_sku_stock_zero}")
    private String topic;

    @Override
    public EventMessage<Long> buildEventMessage(Long sku) {
        return EventMessage.<Long>builder()
                .id(RandomStringUtils.randomNumeric(11))
                .timestamp(new Date())
                .data(sku)
                .build();
    }

    @Override
    public String topic() {
        return topic;
    }

}