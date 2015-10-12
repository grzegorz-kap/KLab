package interpreter.translate.model.instruction;

import interpreter.types.ObjectData;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Instruction {

    private InstructionCode instructionCode;
    private Integer argumentsNumber;
    private List<ObjectData> objectDataList = new ArrayList<>();

    public Instruction() {
    }

    public Instruction(InstructionCode instructionCode, Integer argumentsNumber) {
        this.instructionCode = instructionCode;
        this.argumentsNumber = argumentsNumber;
    }

    public InstructionCode getInstructionCode() {
        return instructionCode;
    }

    public void setInstructionCode(InstructionCode instructionCode) {
        this.instructionCode = instructionCode;
    }

    public Integer getArgumentsNumber() {
        return argumentsNumber;
    }

    public void setArgumentsNumber(Integer argumentsNumber) {
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
}
