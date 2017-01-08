package com.klab.interpreter.core.events;

import java.nio.file.WatchEvent;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.StandardWatchEventKinds.*;

public class ScriptChangeEvent extends InterpreterEvent<String> {
    private static final Map<WatchEvent.Kind<?>, Type> TYPE_MAP = new HashMap<WatchEvent.Kind<?>, Type>() {{
        put(ENTRY_CREATE, Type.CREATED);
        put(ENTRY_DELETE, Type.DELETED);
        put(ENTRY_MODIFY, Type.UPDATED);
    }};
    private Type type;

    public ScriptChangeEvent(String data, Object source, WatchEvent.Kind<?> type) {
        super(data, source);
        this.type = TYPE_MAP.get(type);
    }

    public Type getType() {
        return type;
    }

    public enum Type {CREATED, UPDATED, DELETED}
}
