package com.xlm.domain.service.rule.filter.factory;

import com.xlm.domain.model.entity.RuleActionEntity;
import com.xlm.domain.service.annotation.LogicStrategy;
import com.xlm.domain.service.rule.filter.ILogicFilter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xlm
 * 2024/7/19 下午5:56
 */
@Service
public class DefaultLogicFactory {

    public Map<String, ILogicFilter<?>> logicFilterMap = new ConcurrentHashMap<>();

    public DefaultLogicFactory(List<ILogicFilter<?>> logicFilters) {
        logicFilters.forEach(logic -> {
            LogicStrategy strategy = AnnotationUtils.findAnnotation(logic.getClass(), LogicStrategy.class);
            if (null != strategy) {
                logicFilterMap.put(strategy.logicMode().getCode(), logic);
            }
        });
    }

    /**
     * 	1.	(Map<?, ?>)：
     * 这是一个通配符类型转换。logicFilterMap 是一个原始类型的 Map，可能没有指定键和值的类型。通过将它转换为 Map<?, ?>，我们告诉编译器，这个 Map 的键和值可以是任何类型。这是为了消除编译器的类型检查警告，因为直接从原始类型转换为泛型类型是非法的。
     * 	2.	(Map<String, ILogicFilter<T>>)：
     * 这是最终的类型转换，将 Map<?, ?> 转换为 Map<String, ILogicFilter<T>>。这一步是告诉编译器，我们确信 logicFilterMap 实际上是一个 Map，其中键是 String 类型，值是 ILogicFilter<T> 类型。
     * 为什么需要两次类型转换？
     * 直接将 logicFilterMap 从一个原始类型转换为一个带有泛型的类型（如 Map<String, ILogicFilter<T>>）是不允许的，会导致编译错误。因此，通过先将其转换为 Map<?, ?>，我们可以消除编译器的类型检查，然后再进行具体的泛型类型转换。
     */
    public <T extends RuleActionEntity.RaffleEntity> Map<String, ILogicFilter<T>> openLogicFilter() {
        return (Map<String, ILogicFilter<T>>) (Map<?, ?>) logicFilterMap;
    }

    @Getter
    @AllArgsConstructor
    public enum LogicModel {

        RULE_WIGHT("rule_weight", "【抽奖前规则】根据抽奖权重返回可抽奖范围KEY", "before"),
        RULE_BLACKLIST("rule_black_list", "【抽奖前规则】黑名单规则过滤，命中黑名单则直接返回", "before"),
        RULE_LOCK("rule_lock", "【抽奖中规则】抽奖n次后，对应奖品可解锁抽奖", "center"),
        RULE_LUCK_AWARD("rule_lock_award", "【抽奖后规则】拿走兜底奖励", "after"),
        ;

        private final String code;
        private final String info;
        private final String type;

        public static boolean isCenter(String code){
            return "center".equals(LogicModel.valueOf(code.toUpperCase()).type);
        }

        public static boolean isAfter(String code){
            return "after".equals(LogicModel.valueOf(code.toUpperCase()).type);
        }

    }

}
