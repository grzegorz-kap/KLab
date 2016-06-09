package com.klab.interpreter.service.functions;

import com.klab.interpreter.types.scalar.NumberScalarFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractInternalFunction implements InternalFunction {
    protected NumberScalarFactory numberScalarFactory;
    private int address;
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
    public int getAddress() {
        return address;
    }

    @Override
    public void setAddress(int address) {
        this.address = address;
    }

    @Autowired
    public void setNumberScalarFactory(NumberScalarFactory numberScalarFactory) {
        this.numberScalarFactory = numberScalarFactory;
    }
}
