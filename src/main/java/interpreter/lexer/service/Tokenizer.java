package interpreter.lexer.service;

import interpreter.lexer.model.TokenList;

public interface Tokenizer {
    TokenList readTokens(String inputText);
}
