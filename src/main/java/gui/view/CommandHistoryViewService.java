package gui.view;

import gui.model.CommandHistory;
import gui.service.CommandHistoryService;
import org.fxmisc.richtext.CodeArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CommandHistoryViewService {

    private CommandHistory commandHistory;
    private CodeArea commandInput;

    private CommandHistoryService commandHistoryService;


    public void onCommandSubmit() {
        commandHistoryService.addCommand(commandHistory, commandInput.getText());
    }

    public void onArrowUp() {
        setCommandInputText(commandHistoryService.keyUp(commandHistory));
    }

    public void onArrowDown() {
        setCommandInputText(commandHistoryService.keyDown(commandHistory));
    }

    private void setCommandInputText(String command) {
        if (Objects.nonNull(command)) {
            commandInput.clear();
            commandInput.appendText(command);
        }
    }

    public void setCommandInput(CodeArea commandInput) {
        this.commandInput = commandInput;
    }

    public void setCommandHistory(CommandHistory commandHistory) {
        this.commandHistory = commandHistory;
    }

    @Autowired
    public void setCommandHistoryService(CommandHistoryService commandHistoryService) {
        this.commandHistoryService = commandHistoryService;
    }
}
