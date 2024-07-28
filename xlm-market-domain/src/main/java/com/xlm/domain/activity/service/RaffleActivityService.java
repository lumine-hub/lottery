package com.xlm.domain.activity.service;

import com.xlm.domain.activity.repository.IActivityRepository;
import org.springframework.stereotype.Service;


/**
 * @author xlm
 * 2024/7/28 下午4:12
 * 抽奖活动抽象类，抽奖活动服务
 */
@Service
public class RaffleActivityService extends AbstractRaffleActivity {

    public RaffleActivityService(IActivityRepository activityRepository) {
        super(activityRepository);
    }

}
