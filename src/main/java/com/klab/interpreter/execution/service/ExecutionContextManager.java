package com.klab.interpreter.execution.service;

import com.klab.interpreter.execution.model.ExecutionContext;
import com.klab.interpreter.types.ObjectData;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExecutionContextManager {

    public List<ObjectData> executionStackPop(ExecutionContext executionContext, int elementsCount) {
        List<ObjectData> objectDataList = new ArrayList<>(elementsCount);
        while (elementsCount-- > 0) {
            objectDataList.add(0, executionContext.executionStackPop());
        }
        return objectDataList;
    }
}
