package com.xlm.domain.activity.service.rule;

/**
 * @author xlm
 * 2024/7/28 下午9:49
 */
public interface IActionChainArmory {

    IActionChain next();

    IActionChain appendNext(IActionChain next);

}
