package interpreter.commons;

import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class IdentifierMapperImpl implements IdentifierMapper {
    private Integer mainIdentifierFreeAddress = 0;
    private Integer internalFunctionFreeAddress = 0;
    private Integer externalFunctionFreeAddress = 0;
    private Map<String, Integer> mainIdentifierAddressMap = new HashMap<>();
    private Map<String, Integer> internalFunctionAddressMap = new HashMap<>();
    private Map<String, Integer> externalFunctionAddressMap = new HashMap<>();

    private Deque<Map<String, Integer>> scopes = new ArrayDeque<>();
    private Deque<Integer> scopesAddress = new ArrayDeque<>();

    @Override
    public void putNewScope() {
        scopes.addFirst(mainIdentifierAddressMap);
        scopesAddress.addFirst(mainIdentifierFreeAddress);
        mainIdentifierAddressMap = Maps.newHashMap();
        mainIdentifierFreeAddress = 0;
    }

    @Override
    public void restorePreviousScope() {
        mainIdentifierAddressMap = scopes.removeFirst();
        mainIdentifierFreeAddress = scopesAddress.removeFirst();
    }

    @Override
    public int mainMappingsSize() {
        return mainIdentifierAddressMap.size();
    }

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
}
