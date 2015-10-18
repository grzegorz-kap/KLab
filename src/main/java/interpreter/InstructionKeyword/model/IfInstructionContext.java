package interpreter.InstructionKeyword.model;

import interpreter.translate.model.instruction.JumperInstruction;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class IfInstructionContext {

    private Deque<IfContext> ifContexts = new ArrayDeque<>();

    public void addIf() {
        ifContexts.push(new IfContext());
    }

    public void addEndIfJumper(JumperInstruction jumperInstruction) {
        ifContexts.peek().addEndIfJumper(jumperInstruction);
    }

    public void setJumpOnFalse(JumperInstruction jumperInstruction) {
        ifContexts.peek().setJumpOnFalse(jumperInstruction);
    }

    public int size() {
        return ifContexts.size();
    }

    private static class IfContext {
        private List<JumperInstruction> endIfJumps = new ArrayList<>();
        private JumperInstruction jumpOnFalse;

        public void addEndIfJumper(JumperInstruction jumperInstruction) {
            endIfJumps.add(jumperInstruction);
        }

        public JumperInstruction getJumpOnFalse() {
            return jumpOnFalse;
        }

        public void setJumpOnFalse(JumperInstruction jumpOnFalse) {
            this.jumpOnFalse = jumpOnFalse;
        }
    }
}
