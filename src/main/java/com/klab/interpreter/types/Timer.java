package com.klab.interpreter.types;

public class Timer implements ObjectData {
    private Long counter;
    private String name;
    private int address;

    public void start() {
        counter = System.nanoTime();
    }

    public Long stop() {
        Long interval = System.nanoTime() - counter;
        counter = null;
        return interval / 1_000_000L;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setAddress(int address) {
        this.address = address;
    }

    @Override
    public int getAddress() {
        return address;
    }
}
