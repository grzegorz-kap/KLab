package com.klab.interpreter.profiling.model;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

public class ProfilingReport {
    private List<CodeReport> codeReports = Lists.newArrayList();

    public void add(CodeReport codeReport) {
        codeReports.add(codeReport);
    }

    public List<CodeReport> getCodeReports() {
        return Collections.unmodifiableList(codeReports);
    }
}
