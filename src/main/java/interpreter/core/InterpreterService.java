package interpreter.core;

import interpreter.commons.utils.ExpressionPrinter;
import interpreter.commons.utils.MacroInstructionPrinter;
import interpreter.execution.service.ExecutionService;
import interpreter.lexer.model.TokenList;
import interpreter.lexer.service.Tokenizer;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.parsing.service.Parser;
import interpreter.translate.model.MacroInstruction;
import interpreter.translate.service.InstructionTranslator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InterpreterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InterpreterService.class);

    private ExecutionService executionService;
    private Parser parser;
    private Tokenizer tokenizer;
    private InstructionTranslator instructionTranslator;
    private ExpressionPrinter expressionPrinter;
    private MacroInstructionPrinter macroInstructionPrinter;

    public void start(String input) {
        try {
            startExecution(input);
        } catch (RuntimeException ex) {
            LOGGER.error("Execution failed", ex);
        }
    }

    private void startExecution(String input) {
        LOGGER.info("\n{}", input);
        executionService.resetCodeAndStack();
        TokenList tokenList = tokenizer.readTokens(input);
        Expression<ParseToken> expression = parser.process(tokenList);
        LOGGER.info("\n{}", expressionPrinter.expressionToString(expression));
        MacroInstruction macroInstruction = instructionTranslator.translate(expression);
        LOGGER.info("\n{}", macroInstructionPrinter.print(macroInstruction));
        executionService.addInstructions(macroInstruction.getInstructions());
        executionService.start();
    }

    @Autowired
    public void setExecutionService(ExecutionService executionService) {
        this.executionService = executionService;
    }

    @Autowired
    public void setTokenizer(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    @Autowired
    public void setParser(Parser parser) {
        this.parser = parser;
    }

    @Autowired
    public void setInstructionTranslator(InstructionTranslator instructionTranslator) {
        this.instructionTranslator = instructionTranslator;
    }

    @Autowired
    public void setExpressionPrinter(ExpressionPrinter expressionPrinter) {
        this.expressionPrinter = expressionPrinter;
    }

    @Autowired
    public void setMacroInstructionPrinter(MacroInstructionPrinter macroInstructionPrinter) {
        this.macroInstructionPrinter = macroInstructionPrinter;
    }
}
