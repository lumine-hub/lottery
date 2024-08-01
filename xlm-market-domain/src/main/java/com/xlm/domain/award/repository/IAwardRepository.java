package com.xlm.domain.award.repository;

import com.xlm.domain.award.model.aggregate.UserAwardRecordAggregate;

/**
 * @author xlm
 * 2024/8/1 下午2:30
 */
public interface IAwardRepository {

    void saveUserAwardRecord(UserAwardRecordAggregate userAwardRecordAggregate);

}
