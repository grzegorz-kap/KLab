package com.klab.interpreter.parsing.model;

import java.util.ArrayDeque;
import java.util.Deque;

public class BalanceContext {
    private Deque<BalanceType> balanceTypes = new ArrayDeque<>();
    private Deque<KeywordBalance> keywordBalance = new ArrayDeque<>();

    public void put(KeywordBalance balance) {
        keywordBalance.addFirst(balance);
    }

    public KeywordBalance popKeyword() {
        return keywordBalance.removeFirst();
    }

    public boolean isKeywordBalance(KeywordBalance balance) {
        return keywordBalance.stream().filter(value -> value.equals(balance)).findFirst().orElse(null) != null;
    }

    public void put(BalanceType balanceType) {
        balanceTypes.addFirst(balanceType);
    }

    public BalanceType pop() {
        return balanceTypes.removeFirst();
    }

    public BalanceType peek() {
        return balanceTypes.peekFirst();
    }

    public boolean isBalanceType(BalanceType balanceType) {
        return balanceType.equals(balanceTypes.peekFirst());
    }
}
