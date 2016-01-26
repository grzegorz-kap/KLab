package interpreter.core;

import interpreter.execution.service.ExecutionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class InterpreterService {
    private static final Logger LOGGER = LoggerFactory.getLogger(InterpreterService.class);
    private ExecutionService executionService;
    private CodeGenerator codeGenerator;

    public void startExecution(String input) {
        executionService.resetCodeAndStack();
        codeGenerator.translate(input, () -> executionService.getExecutionContext().getCode(), this::execute);
    }

    private void execute() {
        if (codeGenerator.executionCanStart()) {
            executionService.start();
        }
    }

    public void printCode() {
        LOGGER.info("{}", executionService.getExecutionContext().getCode());
    }

    @Autowired
    public void setExecutionService(ExecutionService executionService) {
        this.executionService = executionService;
    }

    @Autowired
    public void setCodeGenerator(CodeGenerator codeGenerator) {
        this.codeGenerator = codeGenerator;
    }
}
