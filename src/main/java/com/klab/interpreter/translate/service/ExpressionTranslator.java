package com.klab.interpreter.translate.service;

import com.klab.interpreter.execution.model.Code;
import com.klab.interpreter.parsing.model.ParseToken;
import com.klab.interpreter.parsing.model.expression.Expression;
import com.klab.interpreter.translate.model.MacroInstruction;

public interface ExpressionTranslator {
    MacroInstruction translate(Expression<ParseToken> expression);

    void setupCode(Code code);
}
