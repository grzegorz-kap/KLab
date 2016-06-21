package com.klab.interpreter.translate.model;

import com.klab.interpreter.debug.Breakpoint;
import com.klab.interpreter.lexer.model.CodeAddress;
import com.klab.interpreter.profiling.model.ProfilingData;
import com.klab.interpreter.types.ObjectData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Instruction {
    private InstructionCode instructionCode;
    private boolean print;
    private List<ObjectData> data = new ArrayList<>();
    private int argumentsNumber = 0;
    private Breakpoint breakpoint = null;
    private CodeAddress codeAddress;
    private ProfilingData<Instruction> profilingData;

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

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append(instructionCode);
        b.append('\t');
        data.forEach(objectData -> b.append(objectData).append("\t"));
        if (profilingData != null) {
            b.append('\t').append(profilingData);
        }
        return b.toString();
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
        return breakpoint != null;
    }

    public Breakpoint getBreakpoint() {
        return breakpoint;
    }

    public void setBreakpoint(Breakpoint breakpoint) {
        this.breakpoint = breakpoint;
    }

    public CodeAddress getCodeAddress() {
        return codeAddress;
    }

    public void setCodeAddress(CodeAddress codeAddress) {
        this.codeAddress = codeAddress;
    }

    public ProfilingData<Instruction> getProfilingData() {
        return profilingData;
    }

    public void setProfilingData(ProfilingData<Instruction> profilingData) {
        this.profilingData = profilingData;
    }

    public boolean isPrint() {
        return print;
    }

    public void setPrint(boolean print) {
        this.print = print;
    }
}
