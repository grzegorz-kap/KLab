package interpreter.service.functions;

public abstract class AbstractInternalFunction implements InternalFunction {

    private Integer address;
    private int argsMin;
    private int argsMax;
    private String name;

    public AbstractInternalFunction(int argsMin, int argsMax, String name) {
        this.argsMin = argsMin;
        this.argsMax = argsMax;
        this.name = name;
    }

    @Override
    public int getMinArgs() {
        return argsMin;
    }

    @Override
    public int getMaxArgs() {
        return argsMax;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer getAddress() {
        return address;
    }

    @Override
    public void setAddress(Integer address) {
        this.address = address;
    }
}
