package com.klab.interpreter.functions;

import com.klab.interpreter.commons.memory.IdentifierMapper;
import com.klab.interpreter.commons.memory.MemorySpace;
import com.klab.interpreter.types.ObjectData;
import com.klab.interpreter.types.StringData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component
public class ClearFunction extends AbstractInternalFunction {
    private MemorySpace memorySpace;
    private IdentifierMapper identifierMapper;

    public ClearFunction() {
        super(0, Integer.MAX_VALUE, "clear");
    }

    @Override
    public ObjectData call(ObjectData[] data, int expectedOutput) {
        if (data.length == 0) {
            IntStream.range(0, memorySpace.size()).forEach(index -> {
                memorySpace.set(index, null, null);
            });
        } else {
            Stream.of(data)
                    .filter(objectData -> objectData instanceof StringData)
                    .map(StringData.class::cast)
                    .map(StringData::getData)
                    .map(identifierMapper::getMainAddress)
                    .forEach(address -> memorySpace.set(address, null, null));
            Stream.of(data)
                    .filter(objectData -> !(objectData instanceof StringData))
                    .map(memorySpace::find)
                    .filter(integer -> integer != -1)
                    .forEach(integer -> memorySpace.set(integer, null, null));
        }
        System.gc();
        return null;
    }

    @Autowired
    public void setMemorySpace(MemorySpace memorySpace) {
        this.memorySpace = memorySpace;
    }

    @Autowired
    public void setIdentifierMapper(IdentifierMapper identifierMapper) {
        this.identifierMapper = identifierMapper;
    }
}
