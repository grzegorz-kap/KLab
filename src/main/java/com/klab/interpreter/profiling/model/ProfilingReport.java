package com.klab.interpreter.profiling.model;

import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProfilingReport {
    private Map<String, CodeReport> codeReports = Maps.newHashMap();

    public void add(CodeReport codeReport) {
        codeReports.put(codeReport.getTitle(), codeReport);
    }

    public CodeReport getCodeReport(String title) {
        return codeReports.get(title);
    }

    public List<CodeReport> getCodeReports() {
        return new ArrayList<>(codeReports.values());
    }
}
