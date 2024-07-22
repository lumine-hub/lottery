package com.xlm.types.common;

public class Constants {

    public final static String SPLIT = ",";
    public final static String COLON = ":";
    public final static String SPACE = " ";

    public static class RedisKey {
        // 存放每一种策略的数据，key为strategyId, value为StrategyEntity
        public static String STRATEGY_KEY = "big_market_strategy_key_";

        // 存放每一种策略的奖品信息 key为strategyId，value为List<StrategyAward>
        public static String STRATEGY_AWARD_KEY = "big_market_strategy_award_key_";

        // 存放每一种策略的hash表信息，key为strategyId或者strategyId_4000,4000表示用户已经抽了多少积分，
        // 走这个积分下的奖品，value是一个hash表，hash表的key是一个数字，value是awardId
        // 比如你抽了一个数字52，52对应的奖品awardId是101号奖品
        public static String STRATEGY_RATE_TABLE_KEY = "big_market_strategy_rate_table_key_";

        // 存放每一种策略的抽奖范围，key为strategyId或者strategyId_4000， value是一个整数。
        // 比如，key是100001，value是250，那么之后别人获取随机数就是1-250
        public static String STRATEGY_RATE_RANGE_KEY = "big_market_strategy_rate_range_key_";

        // 存放的是运用于规则中抽奖的tree模型
        public static String RULE_TREE_VO_KEY = "rule_tree_vo_key_";
    }

}
