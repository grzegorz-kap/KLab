package com.klab.interpreter.profiling.model;

import com.google.common.collect.Lists;

import java.util.List;

public class ProfilingReport {
    private List<CodeReport> codeReports = Lists.newArrayList();

    public void add(CodeReport codeReport) {
        codeReports.add(codeReport);
    }
}
