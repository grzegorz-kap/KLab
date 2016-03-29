package com.klab.interpreter.parsing.service;

import com.klab.interpreter.parsing.model.BalanceContext;
import com.klab.interpreter.parsing.model.BalanceType;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class BalanceContextService {

    public void add(ParseContextManager parseContextManager, BalanceType balanceType) {
        BalanceContext balanceContext = parseContextManager.getBalanceContext();
        balanceContext.put(balanceType);
    }

    public void popOrThrow(ParseContextManager parseContextManager, BalanceType balanceType) {
        BalanceContext balanceContext = parseContextManager.getBalanceContext();
        BalanceType current = balanceContext.pop();
        checkIfCorrect(current, balanceType);
    }

    public BalanceType peek(ParseContextManager parseContextManager) {
        return parseContextManager.getBalanceContext().peek();
    }

    private void checkIfCorrect(BalanceType current, BalanceType expected) {
        if (!expected.equals(current)) {
            throw new RuntimeException(expected.toString());
        }
    }
}
