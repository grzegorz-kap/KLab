package com.klab.interpreter.service.functions.external;

class FunctionMapKey {
    public String name;
    public int arguments;
    public int expected;

    FunctionMapKey(String name, int arguments, int expected) {
        this.name = name;
        this.arguments = arguments;
        this.expected = expected;
    }

    @Override
    public boolean equals(Object obj) {
        FunctionMapKey key = obj instanceof FunctionMapKey ? ((FunctionMapKey) obj) : null;
        return key != null && this.expected == key.expected &&
                key.arguments == this.arguments &&
                key.name.equals(this.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
