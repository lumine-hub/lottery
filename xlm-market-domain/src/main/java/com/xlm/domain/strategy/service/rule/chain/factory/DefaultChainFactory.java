package com.xlm.domain.strategy.service.rule.chain.factory;

import com.xlm.domain.strategy.model.entity.StrategyEntity;
import com.xlm.domain.strategy.repository.IStrategyRepository;
import com.xlm.domain.strategy.service.rule.chain.ILogicChain;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xlm
 * 2024/7/21 下午4:29
 * 工厂维护一个map，key是规则（如rule_weight），value是对应的IlogicChain
 * 提供一个open方法，传入strategyId，返回IlogicChain
 */
@Component
public class DefaultChainFactory {

    Map<String, ILogicChain> chainMap = new ConcurrentHashMap<>();
    @Resource
    private IStrategyRepository repository;

    public DefaultChainFactory(Map<String, ILogicChain> chainMap, IStrategyRepository repository) {
        this.chainMap = chainMap;
        this.repository = repository;
    }

    public ILogicChain openLogicChain(Long strategyId) {
        StrategyEntity strategyEntity = repository.queryStrategyEntityByStrategyId(strategyId);
        String[] ruleModels = strategyEntity.ruleModels();
        if (ruleModels == null || ruleModels.length == 0) {
            return chainMap.get("default");
        }
        // 按照配置顺序装填用户配置的责任链；rule_blacklist、rule_weight 「注意此数据从Redis缓存中获取，如果更新库表，记得在测试阶段手动处理缓存」
        ILogicChain logicChain = chainMap.get(ruleModels[0]);
        ILogicChain current = logicChain;
        for (int i = 1; i < ruleModels.length; i++) {
            ILogicChain nextChain = chainMap.get(ruleModels[i]);
            current = current.appendNext(nextChain);
        }

        // 责任链的最后装填默认责任链
        current.appendNext(chainMap.get("default"));
        // logicChain是整条链的起点， current是后面的点，所以要返回起点。
        return logicChain;

    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StrategyAwardVO {
        /** 抽奖奖品ID - 内部流转使用 */
        private Integer awardId;
        /**  */
        private String logicModel;
    }

    @Getter
    @AllArgsConstructor
    public enum LogicModel {

        RULE_DEFAULT("rule_default", "默认抽奖"),
        RULE_BLACKLIST("rule_blacklist", "黑名单抽奖"),
        RULE_WEIGHT("rule_weight", "权重规则"),
        ;

        private final String code;
        private final String info;

    }
}
