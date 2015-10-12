package interpreter.commons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class IdentifierMapper {

    private MemorySpace memorySpace;
    private Integer mainIdentifierFreeAddress = 0;
    private Map<String, Integer> mainIdentifierAddressMap = new HashMap<>();

    public Integer registerMainIdentifier(String id) {
        Integer address = mainIdentifierAddressMap.get(id);
        if (Objects.isNull(address)) {
            address = getNextFreeMainAddressAndIncrement();
            mainIdentifierAddressMap.put(id, address);
            memorySpace.reserveNull();
        }
        return address;
    }

    private Integer getNextFreeMainAddressAndIncrement() {
        return mainIdentifierFreeAddress++;
    }

    @Autowired
    private void setMemorySpace(MemorySpace memorySpace) {
        this.memorySpace = memorySpace;
    }
}
