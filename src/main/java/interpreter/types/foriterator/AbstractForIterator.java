package interpreter.types.foriterator;

import interpreter.types.ObjectData;

public abstract class AbstractForIterator implements ForIterator {

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public boolean isTrue() {
        return false;
    }

    @Override
    public ObjectData copyObjectData() {
        return null;
    }
}