package com.klab.interpreter.types;

import org.apache.commons.lang3.StringUtils;

public class StringData extends AbstractObjectData implements ObjectData, Sizeable {
    private String data;

    public StringData(String data) {
        this.data = StringUtils.removeStart(data, "'");
        this.data = StringUtils.removeEnd(this.data, "'");
    }

    public String getData() {
        return data;
    }

    @Override
    public boolean isTrue() {
        return StringUtils.isNoneBlank(data);
    }

    @Override
    public boolean ansSupported() {
        return true;
    }

    @Override
    public boolean isTemp() {
        return true;
    }

    @Override
    public void setTemp(boolean temp) {
    }

    @Override
    public String toString() {
        return "'" + data + "'";
    }

    @Override
    public ObjectData copy() {
        return this;
    }


    @Override
    public int getRows() {
        return 1;
    }

    @Override
    public int getColumns() {
        return data.length();
    }

    @Override
    public long getCells() {
        return data.length();
    }
}
