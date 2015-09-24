package interpreter.parsing.model;

import java.util.ArrayDeque;
import java.util.Deque;

public class BalanceContext {

    Deque<BalanceType> balanceTypes = new ArrayDeque<>();

    public void put(BalanceType balanceType) {
        balanceTypes.add(balanceType);
    }

    public BalanceType pop() {
        return balanceTypes.pop();
    }
}
