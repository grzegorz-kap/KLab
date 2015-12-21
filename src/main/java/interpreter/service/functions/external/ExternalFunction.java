package interpreter.service.functions.external;

import interpreter.execution.model.Code;

import java.util.ArrayList;
import java.util.List;

public class ExternalFunction {
    private String name;
    private Integer id;
    private Code code;
    private int memoryLength;
    private List<ExternalArgument> arguments = new ArrayList<>();
    private List<ExternalReturn> returns = new ArrayList<>();

    public Code getCode() {
        return code;
    }

    public ExternalFunction setCode(Code code) {
        this.code = code;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public ExternalFunction setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ExternalFunction setName(String name) {
        this.name = name;
        return this;
    }

    public List<ExternalArgument> getArguments() {
        return arguments;
    }

    public List<ExternalReturn> getReturns() {
        return returns;
    }

    public ExternalFunction addArgument(ExternalArgument argument) {
        arguments.add(argument);
        return this;
    }

    public ExternalFunction addReturn(ExternalReturn externalReturn) {
        returns.add(externalReturn);
        return this;
    }

    public int getMemoryLength() {
        return memoryLength;
    }

    public void setMemoryLength(int memoryLength) {
        this.memoryLength = memoryLength;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("Name: \t").append(name).append("\n");
        b.append("Output: ");
        returns.forEach(r -> b.append(r.getName()).append(" "));
        b.append("\nInput: ");
        arguments.forEach(i -> b.append(i.getName()).append(" "));
        b.append("\nBody: ").append(code);
        return b.toString();
    }
}
