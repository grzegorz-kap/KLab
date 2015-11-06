package interpreter.translate.model;

import interpreter.types.IdentifierObject;

import static interpreter.translate.model.InstructionCode.FLNEXT;

public class FLNextInstruction extends JumperInstruction {
    private IdentifierObject iteratorId;
    private IdentifierObject iteratorData;

    public FLNextInstruction() {
        super(FLNEXT, 0);
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(String.valueOf(FLNEXT)).append("\t")
                .append(iteratorId).append("\t")
                .append(iteratorData).append("\t")
                .append(getJumpIndex())
                .toString();
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
