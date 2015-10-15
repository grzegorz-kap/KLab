package interpreter.core;

import interpreter.core.postparse.PostParseHandler;
import interpreter.lexer.model.TokenList;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.translate.model.MacroInstruction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
class InterpreterService extends AbstractInterpreterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InterpreterService.class);

    private List<PostParseHandler> postParseHandlers;

    public void startExecution(String input) {
        TokenList tokenList = tokenizer.readTokens(input);
        parser.setTokenList(tokenList);
        while (parser.hasNext()) {
            executionLoop();
        }
    }

    public void executionLoop() {
        List<Expression<ParseToken>> expression = parser.process();
        PostParseHandler postParseHandler = findPostParseHandler(expression);
        if (Objects.nonNull(postParseHandler)) {
            addMacroInstruction(postParseHandler.handle(expression, instructionTranslator));
        } else {
            expression.forEach(this::process);
        }
        executionService.start();
    }

    private PostParseHandler findPostParseHandler(List<Expression<ParseToken>> expression) {
        return postParseHandlers.stream()
                .filter(handler -> handler.canBeHandled(expression))
                .findFirst().orElse(null);
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

    @Autowired
    private void setPostParseHandlers(List<PostParseHandler> postParseHandlers) {
        this.postParseHandlers = postParseHandlers;
    }
}
