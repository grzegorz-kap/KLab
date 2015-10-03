package interpreter.execution.service;

import interpreter.commons.ObjectData;
import interpreter.execution.model.ExecutionContext;
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
