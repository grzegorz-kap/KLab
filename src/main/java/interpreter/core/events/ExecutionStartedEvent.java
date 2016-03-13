package interpreter.core.events;

public class ExecutionStartedEvent extends InterpreterEvent<Void> {
    public ExecutionStartedEvent(Object source) {
        super(null, source);
    }
}
