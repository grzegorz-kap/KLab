package interpreter.parsing.model;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

public class BalanceContext {

    Deque<BalanceType> balanceTypes = new ArrayDeque<>();
    Deque<KeywordBalance> keywordBalance = new ArrayDeque<>();
    
    public void put(KeywordBalance balance) {
    	keywordBalance.addFirst(balance);
    }
    
    public KeywordBalance popKeyword() {
    	return keywordBalance.removeFirst();
    }
    
    public KeywordBalance peekKeyword() {
    	return keywordBalance.peekFirst();
    }
    
    public boolean isKeywordBalance(KeywordBalance balance) {
    	return Objects.nonNull(keywordBalance.peekFirst()) && keywordBalance.peekFirst().equals(balance);
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
