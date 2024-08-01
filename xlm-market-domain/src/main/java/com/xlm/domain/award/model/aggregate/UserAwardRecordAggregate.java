package com.xlm.domain.award.model.aggregate;

import com.xlm.domain.award.model.entity.TaskEntity;
import com.xlm.domain.award.model.entity.UserAwardRecordEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xlm
 * 2024/8/1 下午2:20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAwardRecordAggregate {

    private UserAwardRecordEntity userAwardRecordEntity;

    private TaskEntity taskEntity;

}