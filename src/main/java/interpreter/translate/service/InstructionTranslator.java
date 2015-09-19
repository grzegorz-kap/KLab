package interpreter.translate.service;

import interpreter.lexer.model.TokenClass;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.translate.handlers.TranslateHandler;
import interpreter.translate.model.MacroInstruction;

public interface InstructionTranslator {

    MacroInstruction translate(Expression<ParseToken> expression);

    void addTranslateHandler(TokenClass tokenClass, TranslateHandler translateHandler);

    TranslateHandler getTranslateHandler(TokenClass tokenClass);
}
