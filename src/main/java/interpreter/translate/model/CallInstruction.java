package interpreter.translate.model;

import interpreter.parsing.model.tokens.IdentifierToken;

public class CallInstruction extends Instruction {

	private IdentifierToken identifierToken;

	public CallInstruction(IdentifierToken identifierToken) {
		this.identifierToken = identifierToken;
		setInstructionCode(InstructionCode.CALL);
	}

	public Integer getAddress() {
		return identifierToken.getAddress();
	}

	public String getName() {
		return identifierToken.getId();
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.append(identifierToken.getId()).append("@");
		builder.append(identifierToken.getAddress()).append("$");
		builder.append(getArgumentsNumber());
		return builder.toString();
	}

}
