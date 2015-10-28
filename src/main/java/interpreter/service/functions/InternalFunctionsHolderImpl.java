package interpreter.service.functions;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import interpreter.commons.IdentifierMapper;
import interpreter.service.functions.model.InternalFunction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

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
    public Integer getAddress(String functionName, int argumentsNumber) {
        InternalFunction internalFunction = internalFunctions.stream()
                .filter(nameAndArgumentsNumberFilter(functionName, argumentsNumber))
                .findFirst().orElse(null);
        return Objects.isNull(internalFunction) ? null : internalFunction.getAddress();
    }

	
    @Override
    public boolean contains(String functionName, int argumentsNumber) {
        return internalFunctions.stream()
                .filter(nameAndArgumentsNumberFilter(functionName, argumentsNumber))
                .findFirst().orElse(null) != null;
    }

    @Override
    public boolean contains(String functionName) {
        return internalFunctions.stream()
                .filter(nameFilter(functionName))
                .findFirst().orElse(null) != null;
    }
    
    private Predicate<? super InternalFunction> nameFilter(String functionName) {
		return function-> function.getName().equals(functionName);
	}


	private Predicate<? super InternalFunction> nameAndArgumentsNumberFilter(String functionName, int argumentsNumber) {
		return function -> function.getName().equals(functionName) && function.getArgumentsNumber() == argumentsNumber;
	}

    @Override
    public void afterPropertiesSet() throws Exception {
        List<InternalFunction> list = new ArrayList<>(Collections.nCopies(internalFunctions.size(), null));
        internalFunctions.forEach(internalFunction -> {
            Integer address = identifierMapper.registerInternalFunction(
                    internalFunction.getName(), internalFunction.getArgumentsNumber());
            internalFunction.setAddress(address);
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
