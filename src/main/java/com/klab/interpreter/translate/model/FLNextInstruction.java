package com.klab.interpreter.translate.model;

import com.klab.interpreter.types.IdentifierObject;

import static com.klab.interpreter.translate.model.InstructionCode.FLNEXT;

public class FLNextInstruction extends JumperInstruction {
    private IdentifierObject iteratorId;
    private IdentifierObject iteratorData;

    public FLNextInstruction() {
        super(FLNEXT, 0);
    }

    @Override
    public String toString() {
        return String.valueOf(FLNEXT) + "\t" +
                iteratorId + "\t" +
                iteratorData + "\t" +
                getJumpIndex();
    }

    public IdentifierObject getIteratorId() {
        return iteratorId;
    }

    public void setIteratorId(IdentifierObject iteratorId) {
        this.iteratorId = iteratorId;
    }

    public IdentifierObject getIteratorData() {
        return iteratorData;
    }

    public void setIteratorData(IdentifierObject iteratorData) {
        this.iteratorData = iteratorData;
    }
}
