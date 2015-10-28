package interpreter.service.functions.model;

public interface InternalFunction {
	
	String SIZE_FUNCTION = "size";
	String getName();
	int getArgumentsNumber();
	Integer getAddress();
	void setAddress(Integer address);

}
