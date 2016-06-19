package com.klab.interpreter.functions.external;

import com.klab.interpreter.execution.model.Code;
import com.klab.interpreter.translate.model.CallInstruction;

public interface ExternalFunctionService {
    ExternalFunction loadFunction(CallInstruction cI);

    Code loadFromCache(String name);
}
