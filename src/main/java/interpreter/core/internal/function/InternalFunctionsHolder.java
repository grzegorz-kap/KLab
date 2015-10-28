package interpreter.core.internal.function;

public interface InternalFunctionsHolder {
    InternalFunction get(int address);
    Integer getAddress(String functionName);
    boolean contains(String functionName, int argumentsNumber);
    boolean contains(String functionName);
}
