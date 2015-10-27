package interpreter.core.internal.function;

public interface InternalFunctionsHolder {
    InternalFunction get(int address);
    boolean contains(String functionName, int argumentsNumber);
}
