package interpreter.commons;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemorySpace {

    private List<ObjectData> mainIdentifiers = new ArrayList<>();

    void reserveNull() {
        mainIdentifiers.add(null);
    }

    public void set(int address, ObjectData data) {
        mainIdentifiers.set(address, data);
    }

    public ObjectData get(int address) {
        return mainIdentifiers.get(address);
    }
}
