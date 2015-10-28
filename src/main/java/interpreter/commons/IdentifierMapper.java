package interpreter.commons;

public interface IdentifierMapper {

	Integer registerMainIdentifier(String id);
	
	Integer getMainAddress(String id);

	Integer registerInternalFunction(String id, int argumentsNumber);

}