package interpreter.service.functions.external;

import interpreter.service.functions.model.CallInstruction;

public interface ExternalFunctionService {
    ExternalFunction loadFunction(CallInstruction cI);
}
