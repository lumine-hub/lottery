package com.xlm.domain.strategy.service.rule.chain;

/**
 * @author xlm
 * 2024/7/21 下午4:37
 * 装配责任链的接口，原本在IlogicChain，后面单拉出来，为了解耦合
 */
public interface ILogicChainArmory {

    ILogicChain next();

    ILogicChain appendNext(ILogicChain next);
}
