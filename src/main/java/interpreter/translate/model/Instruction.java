package interpreter.translate.model;

import interpreter.lexer.model.CodeAddress;
import interpreter.types.ObjectData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Instruction {
    private InstructionCode instructionCode;
    private List<ObjectData> data = new ArrayList<>();
    private int argumentsNumber = 0;
    private boolean breakpoint = false;
    private CodeAddress codeAddress;

    public Instruction() {
    }

    public Instruction(InstructionCode instructionCode, int argumentsNumber) {
        this.instructionCode = instructionCode;
        this.argumentsNumber = argumentsNumber;
    }

    public Instruction(InstructionCode instructionCode, int argumentsNumber, ObjectData... data) {
        this(instructionCode, argumentsNumber);
        Collections.addAll(this.data, data);
    }

    public void add(ObjectData objectData) {
        data.add(objectData);
    }

    public ObjectData getObjectData(int index) {
        return data.get(index);
    }

    public <T> T getObjectData(int index, Class<T> clazz) {
        return clazz.cast(data.get(index));
    }

    public InstructionCode getInstructionCode() {
        return instructionCode;
    }

    public void setInstructionCode(InstructionCode instructionCode) {
        this.instructionCode = instructionCode;
    }

    public int getArgumentsNumber() {
        return argumentsNumber;
    }

    public void setArgumentsNumber(int argumentsNumber) {
        this.argumentsNumber = argumentsNumber;
    }

    public boolean isBreakpoint() {
        return breakpoint;
    }

    public void setBreakpoint(boolean breakpoint) {
        this.breakpoint = breakpoint;
    }

    public CodeAddress getCodeAddress() {
        return codeAddress;
    }

    public void setCodeAddress(CodeAddress codeAddress) {
        this.codeAddress = codeAddress;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append(instructionCode);
        b.append('\t');
        data.forEach(objectData -> b.append(objectData).append("\t"));
        return b.toString();
    }
}
