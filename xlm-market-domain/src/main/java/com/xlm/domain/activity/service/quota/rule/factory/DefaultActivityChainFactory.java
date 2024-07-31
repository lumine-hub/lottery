package com.xlm.domain.activity.service.quota.rule.factory;

import com.xlm.domain.activity.service.quota.rule.IActionChain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author xlm
 * 2024/7/28 下午9:55
 * 责任链工厂
 */
@Component
public class DefaultActivityChainFactory {

//    @Resource 为什么不用Resource注入呢，因为可能构造器使用actionChainGroup，如果构造起先发生，则空指针异常。
//    private Map<String, IActionChain> actionChainGroup;

    private IActionChain actionChain;

    public DefaultActivityChainFactory(Map<String, IActionChain> actionChainGroup) {
        actionChain = actionChainGroup.get(ActionModel.activity_base_action.code);
        actionChain.appendNext(actionChainGroup.get(ActionModel.activity_sku_stock_action.getCode()));
    }

    public IActionChain openActionChain() {
        return this.actionChain;
    }



    @Getter
    @AllArgsConstructor
    public enum ActionModel {

        activity_base_action("activity_base_action", "活动的库存、时间校验"),
        activity_sku_stock_action("activity_sku_stock_action", "活动sku库存"),
        ;

        private final String code;
        private final String info;

    }
}
