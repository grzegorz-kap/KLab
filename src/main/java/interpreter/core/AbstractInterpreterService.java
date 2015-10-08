package interpreter.core;

import interpreter.commons.utils.ExpressionPrinter;
import interpreter.commons.utils.MacroInstructionPrinter;
import interpreter.execution.service.ExecutionService;
import interpreter.lexer.service.Tokenizer;
import interpreter.parsing.service.Parser;
import interpreter.translate.service.InstructionTranslator;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractInterpreterService {

    protected ExecutionService executionService;
    protected Parser parser;
    protected Tokenizer tokenizer;
    protected InstructionTranslator instructionTranslator;
    protected ExpressionPrinter expressionPrinter;
    protected MacroInstructionPrinter macroInstructionPrinter;

    public void resetCodeAndStack() {
        executionService.resetCodeAndStack();
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
