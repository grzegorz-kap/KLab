package interpreter.core;

import interpreter.lexer.model.TokenList;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.translate.model.MacroInstruction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class InterpreterService extends AbstractInterpreterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InterpreterService.class);
    private TokenList tokenList;

    public void startExecution(String input) {
        tokenList = tokenizer.readTokens(input);
        parser.setTokenList(tokenList);
        while (parser.hasNext()) {
            executionLoop();
        }
    }

    public void executionLoop() {
        List<Expression<ParseToken>> expression = parser.process();
        expression.forEach(this::process);
        executionService.start();
    }

    private void process(Expression<ParseToken> expression) {
        LOGGER.info("\n{}", expressionPrinter.expressionToString(expression));
        MacroInstruction macroInstruction = instructionTranslator.translate(expression);
        LOGGER.info("\n{}", macroInstructionPrinter.print(macroInstruction));
        executionService.addInstructions(macroInstruction.getInstructions());
    }
}
