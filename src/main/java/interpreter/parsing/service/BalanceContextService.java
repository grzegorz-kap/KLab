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

    public void popOrThrow(ParseContextManager parseContextManager, BalanceType balanceType) {
        BalanceContext balanceContext = parseContextManager.getBalanceContext();
        BalanceType current = balanceContext.pop();
        checkIfCorrect(current, balanceType);
    }

    private void checkIfCorrect(BalanceType current, BalanceType expected) {
        if (!expected.equals(current)) {
            throw new RuntimeException(expected.toString());
        }
    }
}
