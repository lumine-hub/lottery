package com.xlm.domain.activity.service.product;

import com.xlm.domain.activity.model.entity.SkuProductEntity;
import com.xlm.domain.activity.repository.IActivityRepository;
import com.xlm.domain.activity.service.IRaffleActivitySkuProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xlm
 * 2024/8/12 下午2:38
 */
@Service
public class RaffleActivitySkuProductService implements IRaffleActivitySkuProductService {

    @Resource
    private IActivityRepository repository;

    @Override
    public List<SkuProductEntity> querySkuProductEntityListByActivityId(Long activityId) {
        return repository.querySkuProductEntityListByActivityId(activityId);
    }

}