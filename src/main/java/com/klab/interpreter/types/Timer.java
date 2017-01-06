package com.klab.interpreter.types;

public class Timer extends AbstractObjectData implements Sizeable {
    private Long counter;
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
    public int getRows() {
        return 1;
    }

    @Override
    public int getColumns() {
        return 1;
    }

    @Override
    public long getCells() {
        return 1;
    }
}
