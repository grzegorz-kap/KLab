package com.klab.interpreter.profiling;

import com.klab.interpreter.execution.model.Code;
import com.klab.interpreter.profiling.model.CodeReport;
import com.klab.interpreter.profiling.model.ProfilingReport;

import java.util.Collection;

public interface ReportService {
    ProfilingReport process(Collection<Code> measured);

    void computeLines(CodeReport codeReport);
}
