package interpreter.lexer.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class TokenList {
    private List<Token> tokens = new ArrayList<>();

    public void add(Token token) {
        tokens.add(token);
    }

    public Token get(int index) {
        return tokens.get(index);
    }

    public void remove(int index) {
        tokens.remove(index);
    }

    public int size() {
        return tokens.size();
    }

    public Stream<Token> stream() {
        return tokens.stream();
    }

    public boolean removeIf(Predicate<? super Token> predicate) {
        return tokens.removeIf(predicate);
    }
}
