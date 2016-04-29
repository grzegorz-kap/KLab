package com.klab.interpreter.analyze;

import com.klab.interpreter.execution.model.Code;

import java.util.List;

public interface CodeLiner {
    List<CodeLine> separate(Code code);
}
