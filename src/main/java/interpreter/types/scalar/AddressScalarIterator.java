package interpreter.types.scalar;

import interpreter.types.AddressIterator;

public class AddressScalarIterator implements AddressIterator {
    private boolean nextFlag = true;
    private long value;

    public AddressScalarIterator(int value) {
        this.value = value;
    }

    @Override
    public boolean hasNext() {
        return nextFlag;
    }

    @Override
    public long getNext() {
        nextFlag = false;
        return value;
    }

    @Override
    public long length() {
        return 1;
    }
}
