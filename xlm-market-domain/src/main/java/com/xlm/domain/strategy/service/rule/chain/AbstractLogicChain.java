package com.xlm.domain.strategy.service.rule.chain;

/**
 * @author xlm
 * 2024/7/21 下午3:40
 */
public abstract class AbstractLogicChain implements ILogicChain{
    private ILogicChain next;

    @Override
    public ILogicChain next() {
        return next;
    }

    @Override
    public ILogicChain appendNext(ILogicChain next) {
        this.next = next;
        return next;
    }

    protected abstract String ruleModel();

}
