package com.klab.interpreter.types;

public class Timer implements ObjectData, Sizeable {
    private Long counter;
    private String name;
    private boolean temp = true;

    public void start() {
        counter = System.nanoTime();
    }

    public Long stop() {
        Long interval = System.nanoTime() - counter;
        counter = null;
        return interval / 1_000_000L;
    }

    @Override
    public boolean isTemp() {
        return temp;
    }

    @Override
    public void setTemp(boolean temp) {
        this.temp = temp;
    }

    @Override
    public ObjectData copy() {
        Timer timer = new Timer();
        timer.counter = counter;
        return timer;
    }

    @Override
    public boolean ansSupported() {
        return false;
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
    public long getRows() {
        return 1;
    }

    @Override
    public long getColumns() {
        return 1;
    }

    @Override
    public long getCells() {
        return 1;
    }
}
