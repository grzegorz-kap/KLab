package interpreter.core;

import interpreter.lexer.model.TokenList;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.translate.keyword.PostParseHandler;
import interpreter.translate.model.MacroInstruction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
class InterpreterService extends AbstractInterpreterService {
    private static final Logger LOGGER = LoggerFactory.getLogger(InterpreterService.class);

    public void startExecution(String input) {
        try {
            TokenList tokenList = tokenizer.readTokens(input);
            parser.setTokenList(tokenList);
            while (parser.hasNext()) {
                executionLoop();
            }
        } finally {
            printCode();
        }
        printCode();
    }

    public void printCode() {
        LOGGER.info("{}", executionService.getExecutionContext().getCode());
    }

    public void executionLoop() {
        List<Expression<ParseToken>> expression = parser.process();
        translate(expression);
        execute();
    }

    private void execute() {
        if (executionCanStart()) {
            executionService.start();
        }
    }

    private void translate(List<Expression<ParseToken>> expression) {
        PostParseHandler postParseHandler = findPostParseHandler(expression);
        if (Objects.nonNull(postParseHandler)) {
            addMacroInstruction(postParseHandler.handle(expression, instructionTranslator));
        } else {
            expression.forEach(this::process);
        }
    }

    private PostParseHandler findPostParseHandler(List<Expression<ParseToken>> expression) {
        return postParseHandlers.stream()
                .filter(handler -> handler.canBeHandled(expression))
                .findFirst().orElse(null);
    }

    private void process(Expression<ParseToken> expression) {
        MacroInstruction macroInstruction = instructionTranslator.translate(expression);
        addMacroInstruction(macroInstruction);
    }

    private void addMacroInstruction(MacroInstruction macroInstruction) {
        executionService.addInstructions(macroInstruction.getInstructions());
    }
}
