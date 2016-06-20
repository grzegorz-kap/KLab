package com.klab.interpreter.translate.handlers;

import com.klab.interpreter.execution.model.Code;
import com.klab.interpreter.parsing.model.ParseToken;
import com.klab.interpreter.parsing.model.expression.Expression;
import com.klab.interpreter.translate.model.MacroInstruction;
import com.klab.interpreter.translate.service.ExpressionTranslator;

import java.util.List;
import java.util.function.Predicate;

public interface PostParseHandler {

    boolean canBeHandled(List<Expression<ParseToken>> expressions);

    boolean isInstructionCompletelyTranslated();

    MacroInstruction handle(List<Expression<ParseToken>> expressions, ExpressionTranslator expressionTranslator);

    void setCode(Code code);

    void reset();

    interface ExpressionPredicate extends Predicate<List<Expression<ParseToken>>> {
    }
}
