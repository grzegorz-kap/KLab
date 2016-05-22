package com.klab.gui.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class CommandHistory {
    private List<Command> commands = new ArrayList<>();
    @JsonIgnore
    private int version = 0;

    @JsonIgnore
    public void add(String commandContent) {
        commands.add(new Command(commandContent));
        refreshLastModified();
    }

    @JsonIgnore
    private void refreshLastModified() {
        this.version = +1;
    }

    @JsonIgnore
    public Command get(int index) {
        return commands.get(index);
    }

    @JsonIgnore
    public int size() {
        return commands.size();
    }

    public List<Command> getCommands() {
        return commands;
    }

    public void setCommands(List<Command> commands) {
        this.commands = commands;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
