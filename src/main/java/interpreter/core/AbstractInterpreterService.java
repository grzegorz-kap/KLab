package interpreter.core;

import interpreter.commons.utils.ExpressionPrinter;
import interpreter.commons.utils.MacroInstructionPrinter;
import interpreter.execution.service.ExecutionService;
import interpreter.lexer.service.Tokenizer;
import interpreter.parsing.service.Parser;
import interpreter.translate.keyword.PostParseHandler;
import interpreter.translate.service.InstructionTranslator;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

public abstract class AbstractInterpreterService {
    protected ExecutionService executionService;
    protected Parser parser;
    protected Tokenizer tokenizer;
    protected InstructionTranslator instructionTranslator;
    protected ExpressionPrinter expressionPrinter;
    protected MacroInstructionPrinter macroInstructionPrinter;
    protected List<PostParseHandler> postParseHandlers;

    public void resetCodeAndStack() {
        executionService.resetCodeAndStack();
    }

    protected boolean executionCanStart() {
        for (PostParseHandler handler : postParseHandlers) {
            if (!handler.executionCanStart()) {
                return false;
            }
        }
        return true;
    }

    @PostConstruct
    private void init() {
        for (PostParseHandler postParseHandler : postParseHandlers) {
            postParseHandler.setCode(executionService.getExecutionContext().getCode());
        }
        instructionTranslator.setCode(executionService.getExecutionContext().getCode());
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

    @Autowired
    public void setPostParseHandlers(List<PostParseHandler> postParseHandlers) {
        this.postParseHandlers = postParseHandlers;
    }
}
