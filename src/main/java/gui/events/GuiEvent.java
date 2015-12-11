package gui.events;

import org.springframework.context.ApplicationEvent;

abstract class GuiEvent<T> extends ApplicationEvent {
    private T data;

    public GuiEvent(T data, Object source) {
        super(source);
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
