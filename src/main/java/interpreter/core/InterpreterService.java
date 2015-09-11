package interpreter.core;

import interpreter.execution.service.ExecutionService;
import interpreter.lexer.model.TokenList;
import interpreter.lexer.service.Tokenizer;
import interpreter.parsing.model.ParseToken;
import interpreter.parsing.model.expression.Expression;
import interpreter.parsing.service.Parser;
import interpreter.translate.model.MacroInstruction;
import interpreter.translate.service.InstructionTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InterpreterService {

    private ExecutionService executionService;
    private Parser parser;
    private Tokenizer tokenizer;
    private InstructionTranslator instructionTranslator;

    public MacroInstruction start(String input) {
        executionService.resetCodeAndStack();
        TokenList tokenList = tokenizer.readTokens(input);
        Expression<ParseToken> expression = parser.process(tokenList);
        MacroInstruction macroInstruction = instructionTranslator.translate(expression);
        executionService.addInstructions(macroInstruction.getInstructions());
        executionService.start();
        return macroInstruction;
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
}
