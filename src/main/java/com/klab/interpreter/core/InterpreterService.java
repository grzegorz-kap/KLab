package com.klab.interpreter.core;

import com.klab.interpreter.core.code.CodeGenerator;
import com.klab.interpreter.execution.service.ExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
class InterpreterService {
    private ExecutionService executionService;
    private CodeGenerator codeGenerator;

    public void startExecution(String input) {
        executionService.resetCodeAndStack();
        codeGenerator.translate(input, () -> executionService.getExecutionContext().getCode(), this::execute);
    }

    private void execute() {
        if (codeGenerator.isInstructionCompletelyTranslated()) {
            executionService.start();
        }
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
