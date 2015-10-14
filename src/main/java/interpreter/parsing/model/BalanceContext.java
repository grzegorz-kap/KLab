package interpreter.parsing.model;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

public class BalanceContext {

    Deque<BalanceType> balanceTypes = new ArrayDeque<>();
    Deque<KeywordBalance> keywordBalance = new ArrayDeque<>();
    
    public void put(KeywordBalance balance) {
    	keywordBalance.add(balance);
    }
    
    public KeywordBalance popKeyword() {
    	return keywordBalance.pop();
    }
    
    public KeywordBalance peekKeyword() {
    	return keywordBalance.peek();
    }
    
    public boolean isKeywordBalance(KeywordBalance balance) {
    	return Objects.nonNull(keywordBalance.peek()) && keywordBalance.peek().equals(balance);
    }

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
