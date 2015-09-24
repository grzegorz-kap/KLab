package interpreter.parsing.service;

import interpreter.parsing.model.BalanceContext;
import interpreter.parsing.model.BalanceType;
import org.springframework.stereotype.Service;

@Service
public class BalanceContextService {

    public void add(ParseContextManager parseContextManager, BalanceType balanceType) {
        BalanceContext balanceContext = parseContextManager.getBalanceContext();
        balanceContext.put(balanceType);
    }

    public void pop(ParseContextManager parseContextManager) {
        parseContextManager.getBalanceContext().pop();
    }
}
