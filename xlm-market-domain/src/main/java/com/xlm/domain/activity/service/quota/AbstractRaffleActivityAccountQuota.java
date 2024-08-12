package com.xlm.domain.activity.service.quota;

import com.xlm.domain.activity.model.aggregate.CreateQuotaOrderAggregate;
import com.xlm.domain.activity.model.entity.*;
import com.xlm.domain.activity.repository.IActivityRepository;
import com.xlm.domain.activity.service.IRaffleActivityAccountQuotaService;
import com.xlm.domain.activity.service.quota.policy.ITradePolicy;
import com.xlm.domain.activity.service.quota.rule.IActionChain;
import com.xlm.domain.activity.service.quota.rule.factory.DefaultActivityChainFactory;
import com.xlm.types.enums.ResponseCode;
import com.xlm.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author xlm
 * 2024/7/28 下午4:13
 * 位于quota（资源配额）包下，所以该抽象类用于定义抽奖额度（抽奖次数）的流程。
 * 比如：createOrder，就是创建用户通过sku获取抽奖机会的订单
 */
@Slf4j
public abstract class AbstractRaffleActivityAccountQuota implements IRaffleActivityAccountQuotaService {
    @Resource
    protected DefaultActivityChainFactory defaultActivityChainFactory;

    protected IActivityRepository activityRepository;

    private final Map<String, ITradePolicy> tradePolicyGroup;

    public AbstractRaffleActivityAccountQuota(IActivityRepository activityRepository, Map<String, ITradePolicy> tradePolicyGroup) {
        this.activityRepository = activityRepository;
        this.tradePolicyGroup = tradePolicyGroup;
    }

    public String createOrder(SkuRechargeEntity skuRechargeEntity) {
        // 1.参数校验
        String userId = skuRechargeEntity.getUserId();
        Long sku = skuRechargeEntity.getSku();
        String outBusinessNo = skuRechargeEntity.getOutBusinessNo();
        if (null == sku || StringUtils.isBlank(userId) || StringUtils.isBlank(outBusinessNo)) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());        }

        // 2. 查询基础信息
        // 2.1 通过sku查询活动信息
        ActivitySkuEntity activitySkuEntity = activityRepository.queryActivitySku(skuRechargeEntity.getSku());
        // 2.2 查询活动信息
        ActivityEntity activityEntity = activityRepository.queryRaffleActivityByActivityId(activitySkuEntity.getActivityId());
        // 2.3 查询次数信息（用户在活动上可参与的次数）
        ActivityCountEntity activityCountEntity = activityRepository.queryRaffleActivityCountByActivityCountId(activitySkuEntity.getActivityCountId());

        // 3.规则校验
        IActionChain actionChain = defaultActivityChainFactory.openActionChain();
        // 为什么不需要返回值？因为false的话直接就异常出去了。
        actionChain.action(activitySkuEntity, activityEntity, activityCountEntity);

        // 4.创建聚合对象(订单对象)
        CreateQuotaOrderAggregate createOrderAggregate = buildOrderAggregate(skuRechargeEntity, activitySkuEntity, activityEntity, activityCountEntity);


        // 5. 交易策略 - 【积分兑换，支付类订单】【返利无支付交易订单，直接充值到账】【订单状态变更交易类型策略】
        ITradePolicy tradePolicy = tradePolicyGroup.get(skuRechargeEntity.getOrderTradeType().getCode());
        tradePolicy.trade(createOrderAggregate);

        // 6. 返回单号
        return createOrderAggregate.getActivityOrderEntity().getOrderId();

    }

    protected abstract void doSaveOrder(CreateQuotaOrderAggregate createQuotaOrderAggregate);

    protected abstract CreateQuotaOrderAggregate buildOrderAggregate(SkuRechargeEntity skuRechargeEntity, ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity);

}
