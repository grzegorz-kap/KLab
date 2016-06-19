package com.klab.interpreter.translate.service;

import com.klab.interpreter.execution.model.Code;
import com.klab.interpreter.parsing.model.ParseClass;
import com.klab.interpreter.parsing.model.ParseToken;
import com.klab.interpreter.parsing.model.expression.Expression;
import com.klab.interpreter.translate.handlers.TranslateHandler;
import com.klab.interpreter.translate.model.MacroInstruction;

public interface ExpressionTranslator {
    MacroInstruction translate(Expression<ParseToken> expression);

    TranslateHandler getTranslateHandler(ParseClass parseClass);

    void setCode(Code code);
}
