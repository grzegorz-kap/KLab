package gui.view;

import org.fxmisc.richtext.CodeArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ConsoleViewService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleViewService.class);

    private CodeArea commandInput;
    private CodeArea consoleOutput;

    public void onCommandSubmit() {
        final String inputText = getInputTextAndClear();
        LOGGER.info("Command: '{}' entered.", inputText);
        appendCommandToConsole(inputText);
    }

    private void appendCommandToConsole(final String command) {
        consoleOutput.appendText(String.format(">> %s \n", command));
    }

    private String getInputTextAndClear() {
        String inputText = commandInput.getText();
        commandInput.clear();
        return inputText;
    }

    public void setCommandInput(CodeArea commandInput) {
        this.commandInput = commandInput;
    }

    public void setConsoleOutput(CodeArea consoleOutput) {
        this.consoleOutput = consoleOutput;
    }
}
