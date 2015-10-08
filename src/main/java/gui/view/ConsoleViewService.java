package gui.view;

import gui.service.InterpreterEventsService;
import interpreter.core.Interpreter;
import interpreter.core.events.PrintEvent;
import org.fxmisc.richtext.CodeArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ConsoleViewService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleViewService.class);
    private Interpreter interpreter;
    private InterpreterEventsService interpreterEventsService;
    private CodeArea commandInput;
    private CodeArea consoleOutput;
    private final ApplicationListener<PrintEvent> PRINT_LISTENER = this::onPrintEvent;

    public void onCommandSubmit() {
        final String inputText = getInputTextAndClear();
        appendCommandToConsole(inputText);
        interpreter.start(inputText);
    }

    private void appendCommandToConsole(final String command) {
        consoleOutput.appendText(String.format(">> %s \n", command));
    }

    private String getInputTextAndClear() {
        String inputText = commandInput.getText();
        commandInput.clear();
        return inputText;
    }

    private void onPrintEvent(PrintEvent printEvent) {
        String printData = printEvent.getObjectData().toString();
        consoleOutput.appendText(String.format("   %s \n", printData));
    }

    public void setCommandInput(CodeArea commandInput) {
        this.commandInput = commandInput;
    }

    public void setConsoleOutput(CodeArea consoleOutput) {
        this.consoleOutput = consoleOutput;
    }

    @PostConstruct
    void init() {
        interpreterEventsService.register(PRINT_LISTENER);
    }

    @PreDestroy
    void destroy() {
        interpreterEventsService.unregister(PRINT_LISTENER);
    }

    @Autowired
    public void setInterpreterEventsService(InterpreterEventsService interpreterEventsService) {
        this.interpreterEventsService = interpreterEventsService;
    }

    @Autowired
    public void setInterpreter(Interpreter interpreter) {
        this.interpreter = interpreter;
    }
}
