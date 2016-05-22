package com.klab.interpreter.service.functions.external;

import com.klab.interpreter.execution.model.Code;
import com.klab.interpreter.service.functions.model.CallInstruction;

import java.util.function.Consumer;

public interface ExternalFunctionService {
    ExternalFunction loadFunction(CallInstruction cI);

    Code loadFunction(String name);

    void addLoadListener(Consumer<ExternalFunction> listener);
}
