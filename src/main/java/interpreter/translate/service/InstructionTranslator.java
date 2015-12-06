package interpreter.translate.service;

import interpreter.execution.model.Code;
import interpreter.parsing.model.ParseClass;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.translate.handlers.TranslateHandler;
import interpreter.translate.model.MacroInstruction;

public interface InstructionTranslator {

    MacroInstruction translate(Expression<ParseToken> expression);

    TranslateHandler getTranslateHandler(ParseClass parseClass);
    
    void setCode(Code code);
}
