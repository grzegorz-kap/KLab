package interpreter.commons;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IdentifierMapperImpl implements IdentifierMapper {

	private MemorySpace memorySpace;
	private Integer mainIdentifierFreeAddress = 0;
	private Integer internalFunctionFreeAddress = 0;
	private Map<String, Integer> mainIdentifierAddressMap = new HashMap<>();
	private Map<DistinctFunction, Integer> internalFunctionAddressMap = new HashMap<>();
	
	@Override
	public Integer getMainAddress(String id) {
		return mainIdentifierAddressMap.get(id);
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
	public Integer registerInternalFunction(String id, int argumentsNumber) {
		DistinctFunction distinctFunction = new DistinctFunction(id, argumentsNumber);
		Integer address = internalFunctionAddressMap.get(distinctFunction);
		if (Objects.isNull(address)) {
			address = getNextFreeInternalFunctionFreeAddressAndIncrement();
			internalFunctionAddressMap.put(distinctFunction, address);
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

	private static class DistinctFunction {

		private String name;
		private Integer argumentsNumber;

		public DistinctFunction(String name, Integer argumentsNumber) {
			this.name = name;
			this.argumentsNumber = argumentsNumber;
		}

		@Override
		public boolean equals(Object second) {
			DistinctFunction b = (DistinctFunction) second;
			return name.equals(b.name) && argumentsNumber.equals(b.argumentsNumber);
		}

		@Override
		public int hashCode() {
			return name.hashCode();
		}

	}
}
