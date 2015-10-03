package gui.service;

import gui.helpers.TimeHelper;
import gui.model.Command;
import gui.model.CommandHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class CommandHistoryService {

    private TimeHelper timeHelper;

    public void addCommand(CommandHistory commandHistory, String commandContent) {
        if (isNewCommand(commandHistory, commandContent)) {
            Command command = new Command();
            command.setContent(commandContent);
            commandHistory.add(command);
            commandHistory.setCurrentPosition(commandHistory.getSize());
        }
    }

    public String keyUp(CommandHistory commandHistory) {
        if (commandHistory.getCurrentPosition() > 0) {
            commandHistory.setCurrentPosition(commandHistory.getCurrentPosition() - 1);
        }
        String content = getCurrentCommandContent(commandHistory);
        return content;
    }

    public String keyDown(CommandHistory commandHistory) {
        if (commandHistory.getCurrentPosition() < commandHistory.getSize() - 1) {
            commandHistory.setCurrentPosition(commandHistory.getCurrentPosition() + 1);
        }
        String content = getCurrentCommandContent(commandHistory);
        return content;
    }

    private String getCurrentCommandContent(CommandHistory commandHistory) {
        Command command = commandHistory.getCurrent();
        return Objects.isNull(command) ? null : command.getContent();
    }


    private boolean isNewCommand(CommandHistory commandHistory, String commandContent) {
        Command lastCommand = commandHistory.getSize() == 0 ? null : commandHistory.getAt(commandHistory.getSize() - 1);
        return Objects.isNull(lastCommand) || !lastCommand.getContent().equals(commandContent) || !isTheSameDay(lastCommand.getCreatedAt());
    }

    private boolean isTheSameDay(LocalDateTime createdAt) {
        return timeHelper.isTheSameDay(createdAt, LocalDateTime.now());
    }

    @Autowired
    public void setTimeHelper(TimeHelper timeHelper) {
        this.timeHelper = timeHelper;
    }
}
