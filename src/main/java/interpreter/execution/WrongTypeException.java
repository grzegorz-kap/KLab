package interpreter.execution;

import interpreter.commons.ObjectData;

public class WrongTypeException extends RuntimeException {

    private ObjectData objectData;

    public WrongTypeException(ObjectData objectData) {
        this.objectData = objectData;
    }
}
