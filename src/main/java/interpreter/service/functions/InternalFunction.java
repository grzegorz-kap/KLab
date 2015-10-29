package interpreter.service.functions;

import interpreter.types.ObjectData;

public interface InternalFunction {
	
	String SIZE_FUNCTION = "size";
	String RAND_FUNCTION = "rand";
	String EYE_FUNCTION = "eye";
	String getName();
	int getMinArgs();
	int getMaxArgs();
	Integer getAddress();
	void setAddress(Integer address);
	ObjectData call(ObjectData[] datas);

}
