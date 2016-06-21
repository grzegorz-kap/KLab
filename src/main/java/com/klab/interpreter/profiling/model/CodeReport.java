package com.klab.interpreter.profiling.model;

import com.klab.interpreter.commons.analyze.CodeLine;
import com.klab.interpreter.execution.model.Code;

import java.util.Map;

public class CodeReport {
    private ProfilingReport parent;
    private Code code;
    private String title;
    private SourceType sourceType;
    private Map<Integer, ProfilingData<CodeLine>> linesProfile;
    private long called;
    private long totalTime;

    public CodeReport(Code code, SourceType sourceType) {
        this.code = code;
        this.sourceType = sourceType;
    }

    public Map<Integer, ProfilingData<CodeLine>> getLinesProfile() {
        return linesProfile;
    }

    public void setLinesProfile(Map<Integer, ProfilingData<CodeLine>> linesProfile) {
        this.linesProfile = linesProfile;
    }

    public void setSourceType(SourceType sourceType) {
        this.sourceType = sourceType;
    }

    public ProfilingReport getParent() {
        return parent;
    }

    public void setParent(ProfilingReport parent) {
        this.parent = parent;
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
