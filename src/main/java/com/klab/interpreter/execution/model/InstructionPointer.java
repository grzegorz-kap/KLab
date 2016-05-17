package com.klab.interpreter.execution.model;

import com.klab.interpreter.translate.model.Instruction;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.stream.Stream;

public class InstructionPointer {
    private Deque<Code> codeDeque = new ArrayDeque<>();
    private Deque<Integer> addressDeque = new ArrayDeque<>();
    private Code code;
    private int address = 0;

    public InstructionPointer(Code code) {
        this.code = code;
    }

    public void moveToCode(Code newCode) {
        codeDeque.addFirst(code);
        addressDeque.addFirst(address);
        code = newCode;
        address = 0;
    }

    public int callLevel() {
        return codeDeque.size();
    }

    public void restorePreviousCode() {
        code = codeDeque.removeFirst();
        address = addressDeque.removeFirst();
    }

    public boolean isCodeEnd() {
        return code.size() <= address;
    }

    public Instruction currentInstruction() {
        return code.getAtAddress(address);
    }

    public void increment() {
        address++;
    }

    public void jumpTo(int address) {
        this.address = address;
    }

    public String getSourceId() {
        return code.getSourceId();
    }

    public Code getCode() {
        return code;
    }

    public Stream<Code> codeStream() {
        return codeDeque.stream();
    }

    public Iterator<Code> stackIterator() {
        return codeDeque.iterator();
    }
}
