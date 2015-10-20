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

    public void startExecution(String input) {
        TokenList tokenList = tokenizer.readTokens(input);
        parser.setTokenList(tokenList);
        while (parser.hasNext()) {
            executionLoop();
        }
        LOGGER.info("{}", executionService.getExecutionContext().getCode());
    }

    public void executionLoop() {
        List<Expression<ParseToken>> expression = parser.process();
        translate(expression);
        execute();
    }

    private void execute() {
        if(executionCanStart()) {
            executionService.start();
        }
    }

    private void translate(List<Expression<ParseToken>> expression) {
        if (ifPostHandler.canBeHandled(expression)) {
            handleIf(expression);
        } else {
            expression.forEach(this::process);
        }
    }

    private void handleIf(List<Expression<ParseToken>> expression) {
        addMacroInstruction(ifPostHandler.handle(expression, instructionTranslator));
    }

    private void process(Expression<ParseToken> expression) {
        LOGGER.info("\n{}", expressionPrinter.expressionToString(expression));
        MacroInstruction macroInstruction = instructionTranslator.translate(expression);
        addMacroInstruction(macroInstruction);
    }

    private void addMacroInstruction(MacroInstruction macroInstruction) {
        LOGGER.info("\n{}", macroInstructionPrinter.print(macroInstruction));
        executionService.addInstructions(macroInstruction.getInstructions());
    }
}
