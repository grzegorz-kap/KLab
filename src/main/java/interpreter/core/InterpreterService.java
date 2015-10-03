package interpreter.core;

import interpreter.lexer.model.TokenList;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.translate.model.MacroInstruction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
class InterpreterService extends AbstractInterpreterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InterpreterService.class);

    public void startExecution(String input) {
        TokenList tokenList = tokenizer.readTokens(input);
        Expression<ParseToken> expression = parser.process(tokenList);
        LOGGER.info("\n{}", expressionPrinter.expressionToString(expression));
        MacroInstruction macroInstruction = instructionTranslator.translate(expression);
        LOGGER.info("\n{}", macroInstructionPrinter.print(macroInstruction));
        executionService.addInstructions(macroInstruction.getInstructions());
        executionService.start();
    }
}
