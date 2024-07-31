package com.xlm.domain.activity.service.quota.rule;

/**
 * @author xlm
 * 2024/7/28 下午9:51
 */
public abstract class AbstractActionChain implements IActionChain{

    private IActionChain next;

    @Override
    public IActionChain next() {
        return next;
    }

    @Override
    public IActionChain appendNext(IActionChain next) {
        this.next = next;
        return next;
    }
}
