package com.klab.interpreter.service.functions.external;

import com.klab.interpreter.execution.model.Code;
import com.klab.interpreter.service.functions.model.CallInstruction;

public interface ExternalFunctionService {
    ExternalFunction loadFunction(CallInstruction cI);

    Code loadFunction(String name);

    Code loadFromCache(String name);
}
