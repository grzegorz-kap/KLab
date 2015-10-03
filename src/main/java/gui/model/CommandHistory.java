package gui.model;

import java.util.ArrayList;
import java.util.List;

public class CommandHistory {

    private List<Command> commands = new ArrayList<>();
    private int currentPosition = -1;

    public void add(Command command) {
        commands.add(command);
    }

    public void setCommands(List<Command> commands) {
        this.commands = commands;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public Command getCurrent() {
        return currentPosition >= 0 ? commands.get(currentPosition) : null;
    }
}
