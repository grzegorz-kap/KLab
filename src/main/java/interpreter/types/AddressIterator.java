package interpreter.types;

public interface AddressIterator {
    boolean hasNext();

    long getNext();

    long length();

    void reset();
}
