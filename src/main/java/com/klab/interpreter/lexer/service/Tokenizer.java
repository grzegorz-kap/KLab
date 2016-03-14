package com.klab.interpreter.lexer.service;

import com.klab.interpreter.lexer.model.TokenList;

public interface Tokenizer {
    TokenList readTokens(String inputText);
}
