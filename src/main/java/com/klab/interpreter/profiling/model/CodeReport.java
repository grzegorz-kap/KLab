package com.klab.interpreter.profiling.model;

import com.klab.interpreter.execution.model.Code;

public class CodeReport {
    private Code code;
    private String title;
    private SourceType sourceType;
    private long called;
    private long totalTime;

    public CodeReport(Code code, SourceType sourceType) {
        this.code = code;
        this.sourceType = sourceType;
    }

    public Code getCode() {
        return code;
    }

    public void setCode(Code code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getCalled() {
        return called;
    }

    public void setCalled(long called) {
        this.called = called;
    }

    public SourceType getSourceType() {
        return sourceType;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }
}
