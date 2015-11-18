package interpreter.service.functions.model;

import interpreter.translate.model.Instruction;
import interpreter.translate.model.InstructionCode;

public class CallInstruction extends Instruction {

    private CallToken callToken;

    public CallInstruction(CallToken identifierToken) {
        this.callToken = identifierToken;
        setInstructionCode(InstructionCode.CALL);
    }

    public Integer getVariableAddress() {
        return callToken.getVariableAddress();
    }

    public Integer getInternalFunctionAddress() {
        return callToken.getInternalFunctionAddress();
    }

    public String getName() {
        return callToken.getCallName();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append(callToken.getCallName()).append("@");
        builder.append(callToken.getVariableAddress()).append("$");
        builder.append(callToken.getInternalFunctionAddress()).append("#");
        builder.append(getArgumentsNumber());
        return builder.toString();
    }

}
