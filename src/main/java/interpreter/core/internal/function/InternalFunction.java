package interpreter.core.internal.function;

public interface InternalFunction {
	
	String SIZE_FUNCTION = "size";
	String getName();
	int getArgumentsNumber();
	Integer getAddress();
	void setAddress(Integer address);

}
