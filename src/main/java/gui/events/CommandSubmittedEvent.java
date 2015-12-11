package gui.events;

public class CommandSubmittedEvent extends GuiEvent<String> {
    public CommandSubmittedEvent(String data, Object source) {
        super(data, source);
    }
}
