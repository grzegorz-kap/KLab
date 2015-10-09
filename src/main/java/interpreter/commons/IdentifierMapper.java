package interpreter.commons;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class IdentifierMapper {

    private Integer mainIdentifierFreeAddress = 0;
    private Map<String, Integer> mainIdentifierAddressMap = new HashMap<>();

    public Integer registerMainIdentifier(String id) {
        Integer address = mainIdentifierAddressMap.get(id);
        if (Objects.isNull(address)) {
            address = getNextFreeMainAddressAndIncrement();
            mainIdentifierAddressMap.put(id, address);
        }
        return address;
    }

    private Integer getNextFreeMainAddressAndIncrement() {
        return mainIdentifierFreeAddress++;
    }
}
