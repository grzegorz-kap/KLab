package interpreter.service.functions;

import interpreter.types.ObjectData;
import org.springframework.stereotype.Component;

@Component
public class TicFunction extends AbstractInternalFunction {
    public TicFunction() {
        super(0, 1, TIC);
    }

    @Override
    public ObjectData call(ObjectData[] data, int outputSize) {
        return null;
    }
}
