package com.klab.gui.model;

public class CommandHistoryIterator {
    private CommandHistory commandHistory;
    private int index = -1;
    private int version = 0;

    public CommandHistoryIterator(CommandHistory commandHistory) {
        this.commandHistory = commandHistory;
        this.version = commandHistory.getVersion();
    }

    public String next() {
        refreshIndex();
        if (index > 0) {
            index--;
        }
        return current();
    }

    public String prev() {
        refreshIndex();
        if (index < commandHistory.size() - 1) {
            index++;
        }
        return current();
    }

    private String current() {
        return index >= 0 && commandHistory.size() > 0 ? commandHistory.get(index).getContent() : "";
    }

    private void refreshIndex() {
        if (version != commandHistory.getVersion()) {
            version = commandHistory.getVersion();
            index = commandHistory.size();
        }
    }
}
