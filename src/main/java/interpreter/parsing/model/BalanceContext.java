package interpreter.parsing.model;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

public class BalanceContext {

    Deque<BalanceType> balanceTypes = new ArrayDeque<>();

    public void put(BalanceType balanceType) {
        balanceTypes.add(balanceType);
    }

    public BalanceType pop() {
        return balanceTypes.pop();
    }

    public BalanceType peek() {
        return balanceTypes.peek();
    }

    public boolean isBalanceType(BalanceType balanceType) {
        return Objects.nonNull(balanceTypes.peek()) && balanceTypes.peek().equals(balanceType);
    }
}
