package interpreter.core.internal.function;

import interpreter.commons.IdentifierMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class InternalFunctionsHolderImpl implements InitializingBean, InternalFunctionsHolder {

    private List<InternalFunction> internalFunctions;
    private IdentifierMapper identifierMapper;

    @Override
    public InternalFunction get(int address) {
        return address<internalFunctions.size() ? internalFunctions.get(address) : null;
    }

    @Override
    public boolean contains(String functionName, int argumentsNumber) {
        return internalFunctions.stream()
                .filter(function -> function.getName().equals(functionName) && function.getArgumentsNumber() == argumentsNumber)
                .findFirst().orElse(null) != null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<InternalFunction> list = new ArrayList<>(Collections.nCopies(internalFunctions.size(), null));
        internalFunctions.forEach(internalFunction -> {
            Integer address = identifierMapper.registerInternalFunction(
                    internalFunction.getName(), internalFunction.getArgumentsNumber());
            list.set(address, internalFunction);
        });
        internalFunctions = list;
    }

    @Autowired
    public void setInternalFunctions(List<InternalFunction> internalFunctions) {
        this.internalFunctions = internalFunctions;
    }

    @Autowired
    public void setIdentifierMapper(IdentifierMapper identifierMapper) {
        this.identifierMapper = identifierMapper;
    }
}
