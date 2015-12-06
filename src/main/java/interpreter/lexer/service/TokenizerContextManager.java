package interpreter.lexer.service;

import interpreter.lexer.model.Token;
import interpreter.lexer.model.TokenClass;
import interpreter.lexer.model.TokenizerContext;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class TokenizerContextManager {
    private TokenizerContext tokenizerContext;

    public void addToken(Token token) {
        token.setLine(tokenizerContext.getLine());
        token.setColumn(tokenizerContext.getColumn());
        tokenizerContext.addToken(token);
        incrementTextPosition(token.getLexeme().length());
        process(token);
    }

    public TokenClass tokenClassAt(int offset) {
        int index = tokenizerContext.getTokenList().size() + offset - 1;
        return index < 0 || index >= tokenizerContext.getTokenList().size()
                ? null : tokenizerContext.getTokenList().get(index).getTokenClass();
    }

    private void process(final Token token) {
        switch (token.getTokenClass()) {
            case NEW_LINE:
                newLineProcess(token);
                break;
            default:
                standardProcess(token);
        }
    }

    private void standardProcess(final Token token) {
        incrementColumn(token.getLexeme().length());
    }

    private void newLineProcess(final Token token) {
        int newLines = (int) token.getLexeme().chars().filter(character -> character == '\n').count();
        int lastLine = token.getLexeme().lastIndexOf('\n');
        incrementLine(newLines);
        tokenizerContext.setColumn((long) (token.getLexeme().length() - lastLine));
    }

    private void incrementLine(int value) {
        tokenizerContext.setLine(tokenizerContext.getLine() + value);
    }

    private void incrementColumn(int value) {
        tokenizerContext.setColumn(tokenizerContext.getColumn() + value);
    }

    private void incrementTextPosition(int value) {
        tokenizerContext.setIndex(tokenizerContext.getIndex() + value);
    }

    public void setTokenizerContext(TokenizerContext tokenizerContext) {
        this.tokenizerContext = tokenizerContext;
    }
}
