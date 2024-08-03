package com.xlm.trigger.http;

import com.xlm.domain.activity.model.entity.PartakeRaffleActivityEntity;
import com.xlm.domain.activity.model.entity.UserRaffleOrderEntity;
import com.xlm.domain.activity.service.IRaffleActivityPartakeService;
import com.xlm.domain.activity.service.armory.IActivityArmory;
import com.xlm.domain.award.model.entity.UserAwardRecordEntity;
import com.xlm.domain.award.model.valobj.AwardStateVO;
import com.xlm.domain.award.service.IAwardService;
import com.xlm.domain.strategy.model.entity.RaffleAwardEntity;
import com.xlm.domain.strategy.model.entity.RaffleFactorEntity;
import com.xlm.domain.strategy.service.IRaffleStrategy;
import com.xlm.domain.strategy.service.armory.IStrategyArmory;
import com.xlm.trigger.api.IRaffleActivityService;
import com.xlm.trigger.api.dto.ActivityDrawRequestDTO;
import com.xlm.trigger.api.dto.ActivityDrawResponseDTO;
import com.xlm.types.enums.ResponseCode;
import com.xlm.types.exception.AppException;
import com.xlm.types.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author xlm
 * 2024/8/3 下午7:54
 * 抽奖活动服务
 */
@Slf4j
@RestController()
@CrossOrigin("${app.config.cross-origin}")
@RequestMapping("/api/${app.config.api-version}/raffle/activity/")
public class RaffleActivityController implements IRaffleActivityService {

    @Resource
    private IRaffleActivityPartakeService raffleActivityPartakeService;
    @Resource
    private IRaffleStrategy raffleStrategy;
    @Resource
    private IAwardService awardService;
    @Resource
    private IActivityArmory activityArmory;
    @Resource
    private IStrategyArmory strategyArmory;

    /**
     * 活动装配，数据预热
     * http://localhost:8091/api/v1/raffle/activity/armory?activityId=100301
     * @param activityId 活动ID
     * @return
     */
    @Override
    @GetMapping("armory")
    public Response<Boolean> armory(@RequestParam Long activityId) {
        try {
            log.info("活动装配，数据预热，开始 activityId:{}", activityId);
            // 1. 活动装配
            activityArmory.assembleActivitySkuByActivityId(activityId);
            // 2. 策略装配
            strategyArmory.assembleLotteryStrategyByActivityId(activityId);
            Response<Boolean> response = Response.<Boolean>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(true)
                    .build();
            log.info("活动装配，数据预热，完成 activityId:{}", activityId);
            return response;
        } catch (Exception e) {
            log.error("活动装配，数据预热，失败 activityId:{}", activityId, e);
            return Response.<Boolean>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

    /**
     * 抽奖接口
     * http://localhost:8091/api/v1/raffle/activity/draw
     * @param request 请求对象
     * @return
     */
    @Override
    @PostMapping("draw")
    public Response<ActivityDrawResponseDTO> draw(@RequestBody ActivityDrawRequestDTO request) {

        // 1.参数校验
        if (StringUtils.isBlank(request.getUserId()) || null == request.getActivityId()) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
        }

        // 2.参与活动 - 创建参与记录订单
        UserRaffleOrderEntity orderEntity = raffleActivityPartakeService.createOrder(PartakeRaffleActivityEntity.builder()
                .activityId(request.getActivityId())
                .userId(request.getUserId())
                .build());
        // 3. 抽奖策略 - 执行抽奖
        RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(RaffleFactorEntity.builder()
                .userId(orderEntity.getUserId())
                .strategyId(orderEntity.getStrategyId())
                .build());
        // 4. 存放结果 - 写入中奖记录
        UserAwardRecordEntity userAwardRecord = UserAwardRecordEntity.builder()
                .userId(orderEntity.getUserId())
                .activityId(orderEntity.getActivityId())
                .strategyId(orderEntity.getStrategyId())
                .orderId(orderEntity.getOrderId())
                .awardId(raffleAwardEntity.getAwardId())
                .awardTitle(raffleAwardEntity.getAwardTitle())
                .awardTime(new Date())
                .awardState(AwardStateVO.create)
                .build();
        awardService.saveUserAwardRecord(userAwardRecord);
        return null;
    }
}
