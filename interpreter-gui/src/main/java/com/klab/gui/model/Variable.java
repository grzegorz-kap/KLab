package com.klab.gui.model;

import com.klab.interpreter.commons.memory.ObjectWrapper;
import javafx.scene.Node;

public class Variable<N extends Node> {
    private N node;
    private long version;
    private ObjectWrapper objectWrapper;

    public Variable(N node, ObjectWrapper objectWrapper) {
        this.node = node;
        this.version = objectWrapper.getVersion();
        this.objectWrapper = objectWrapper;
    }

    public N getNode() {
        return node;
    }

    public ObjectWrapper getObjectWrapper() {
        return objectWrapper;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
