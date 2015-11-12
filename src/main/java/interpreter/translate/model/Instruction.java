package interpreter.translate.model;

import interpreter.types.ObjectData;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Instruction {
    private InstructionCode instructionCode;
    private int argumentsNumber = 0;
    private List<ObjectData> objectDataList = new ArrayList<>();

    public Instruction() {
    }

    public Instruction(InstructionCode instructionCode, int argumentsNumber) {
        this.instructionCode = instructionCode;
        this.argumentsNumber = argumentsNumber;
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

    public void add(ObjectData objectData) {
        objectDataList.add(objectData);
    }

    public void forEachObjectData(Consumer<? super ObjectData> consumer) {
        objectDataList.forEach(consumer);
    }

    public ObjectData getObjectDate(int index) {
        return objectDataList.get(index);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(instructionCode);
        stringBuilder.append('\t');
        objectDataList.forEach(objectData -> stringBuilder.append(objectData).append("\t"));
        return stringBuilder.toString();
    }
}
