package com.klab.interpreter.service.functions.external;

import com.klab.interpreter.service.functions.model.CallInstruction;

public interface ExternalFunctionService {
    ExternalFunction loadFunction(CallInstruction cI);
}
