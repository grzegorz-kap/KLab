package interpreter.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class Interpreter {
    private static final Logger LOGGER = LoggerFactory.getLogger(Interpreter.class);
    private InterpreterService interpreterService;

    @Async
    public void start(String input) {
        LOGGER.info("\n{}", input);
        try {
            interpreterService.startExecution(input);
        } catch (RuntimeException ex) {
            LOGGER.error("Execution failed", ex);
        }
        interpreterService.printCode();
    }

    @Autowired
    public void setInterpreterService(InterpreterService interpreterService) {
        this.interpreterService = interpreterService;
    }
}
