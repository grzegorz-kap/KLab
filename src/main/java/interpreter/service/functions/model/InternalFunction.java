package interpreter.service.functions.model;

import interpreter.types.ObjectData;

public interface InternalFunction {
	
	String SIZE_FUNCTION = "size";
	String getName();
	int getArgumentsNumber();
	Integer getAddress();
	void setAddress(Integer address);
	ObjectData call(ObjectData[] datas);

}
