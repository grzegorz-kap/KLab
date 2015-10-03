package gui.service;

import gui.model.Command;
import gui.model.CommandHistory;
import org.springframework.stereotype.Service;

@Service
public class CommandHistoryService {

    public void addCommand(CommandHistory commandHistory, String commandContent) {
        Command command = new Command();
        command.setContent(commandContent);
        commandHistory.add(command);
        commandHistory.setCurrentPosition(commandHistory.getSize() - 1);
    }
}
