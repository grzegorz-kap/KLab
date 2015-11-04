package interpreter.service.functions;

public interface InternalFunctionsHolder {
    InternalFunction get(int address);

    Integer getAddress(String functionName, int argumentsNumber);

    boolean contains(String functionName, int argumentsNumber);

    boolean contains(String functionName);
}
