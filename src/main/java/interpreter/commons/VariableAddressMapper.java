package interpreter.commons;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class VariableAddressMapper {

    private Integer mainVariableFreeAddress = 0;
    private Map<String, Integer> mainVariableAddressMap = new HashMap<>();

    public Integer registerMainVariable(String variableName) {
        Integer address = mainVariableAddressMap.get(variableName);
        if (Objects.isNull(address)) {
            address = getNextFreeMainAddressAndIncrement();
            mainVariableAddressMap.put(variableName, address);
        }
        return address;
    }

    private Integer getNextFreeMainAddressAndIncrement() {
        return mainVariableFreeAddress++;
    }
}
