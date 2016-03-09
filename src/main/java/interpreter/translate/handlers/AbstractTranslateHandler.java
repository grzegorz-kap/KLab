package interpreter.translate.handlers;

import interpreter.lexer.model.CodeAddress;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.translate.service.TranslateContextManager;

public abstract class AbstractTranslateHandler implements TranslateHandler {
    protected TranslateContextManager tCM;

    protected static CodeAddress address(Expression<ParseToken> expression) {
        return expression.getValue().getAddress();
    }

    @Override
    public void setTranslateContextManager(TranslateContextManager translateContextManager) {
        this.tCM = translateContextManager;
    }
}
