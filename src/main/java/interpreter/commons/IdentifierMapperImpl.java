package interpreter.commons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class IdentifierMapperImpl implements IdentifierMapper {

    private MemorySpace memorySpace;

    private Integer mainIdentifierFreeAddress = 0;
    private Integer internalFunctionFreeAddress = 0;
    private Integer externalFunctionFreeAddress = 0;
    private Map<String, Integer> mainIdentifierAddressMap = new HashMap<>();
    private Map<String, Integer> internalFunctionAddressMap = new HashMap<>();
    private Map<String, Integer> externalFunctionAddressMap = new HashMap<>();

    @Override
    public Integer getMainAddress(String id) {
        return mainIdentifierAddressMap.get(id);
    }

    @Override
    public Integer registerExternalFunction(String id) {
        Integer address = externalFunctionAddressMap.get(id);
        if (address == null) {
            address = externalFunctionFreeAddress++;
            externalFunctionAddressMap.put(id, address);
        }
        return address;
    }

    @Override
    public Integer getExternalAddress(String id) {
        return externalFunctionAddressMap.get(id);
    }

    @Override
    public Integer registerMainIdentifier(String id) {
        Integer address = mainIdentifierAddressMap.get(id);
        if (Objects.isNull(address)) {
            address = getNextFreeMainAddressAndIncrement();
            mainIdentifierAddressMap.put(id, address);
            memorySpace.reserveNull();
        }
        return address;
    }

    @Override
    public Integer registerInternalFunction(String id) {
        Integer address = internalFunctionAddressMap.get(id);
        if (Objects.isNull(address)) {
            address = getNextFreeInternalFunctionFreeAddressAndIncrement();
            internalFunctionAddressMap.put(id, address);
        }
        return address;
    }

    private Integer getNextFreeMainAddressAndIncrement() {
        return mainIdentifierFreeAddress++;
    }

    private Integer getNextFreeInternalFunctionFreeAddressAndIncrement() {
        return internalFunctionFreeAddress++;
    }

    @Autowired
    private void setMemorySpace(MemorySpace memorySpace) {
        this.memorySpace = memorySpace;
    }
}
