package interpreter.translate.model;

import interpreter.types.IdentifierObject;

import static interpreter.translate.model.InstructionCode.FLNEXT;

public class FLNextInstruction extends JumperInstruction {
    private IdentifierObject dataId;
    private IdentifierObject iteratorId;

    public FLNextInstruction() {
        super(FLNEXT, 0);
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(FLNEXT).append("\t")
                .append(dataId).append("\t")
                .append(iteratorId).append("\t")
                .append(getJumpIndex()).toString();
    }

    public IdentifierObject getIteratorId() {
        return iteratorId;
    }

    public void setIteratorId(IdentifierObject iteratorId) {
        this.iteratorId = iteratorId;
    }

    public IdentifierObject getDataId() {
        return dataId;
    }

    public void setDataId(IdentifierObject dataId) {
        this.dataId = dataId;
    }
}
