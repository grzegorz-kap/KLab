package gui.model;

import gui.helpers.TimeHelper;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CommandHistory {
    private List<Command> commands = new ArrayList<>();
    private int index = -1;

    public void add(String commandContent) {
        if (StringUtils.isNotBlank(commandContent) && isNewCommand(commandContent)) {
            commands.add(new Command(commandContent));
            index = commands.size();
        }
    }

    public String next() {
        if (index > 0) {
            index--;
        }
        return current();
    }

    public String prev() {
        if (index < commands.size() - 1) {
            index++;
        }
        return current();
    }

    public String current() {
        return index >= 0 && size() > 0 ? commands.get(index).getContent() : "";
    }

    public int size() {
        return commands.size();
    }

    private boolean isNewCommand(String commandContent) {
        Command lastCommand = commands.size() == 0 ? null : commands.get(commands.size() - 1);
        return Objects.isNull(lastCommand) ||
                !lastCommand.getContent().equals(commandContent)
                || !TimeHelper.isTheSameDay(lastCommand.getCreatedAt(), LocalDateTime.now());
    }
}
