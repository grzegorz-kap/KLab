package interpreter.InstructionKeyword.model;

import interpreter.translate.model.JumperInstruction;

import java.util.ArrayDeque;
import java.util.Deque;

public class ForInstructionContext {
    private Deque<ForIterator> iterators = new ArrayDeque<>();

    public void push(int flhnextAddress, JumperInstruction falseJumper) {
        iterators.addFirst(new ForIterator(flhnextAddress, falseJumper));
    }

    public int getflhNextAddress() {
        return iterators.peekFirst().flhnextAddress;
    }

    public JumperInstruction getJumpOnFalse() {
        return iterators.peekFirst().falseJumper;
    }

    public void pop() {
        iterators.removeFirst();
    }

    public int size() {
        return iterators.size();
    }

    private static class ForIterator {
        public int flhnextAddress;
        public JumperInstruction falseJumper;

        public ForIterator(int flhnextAddress, JumperInstruction falseJumper) {
            this.flhnextAddress = flhnextAddress;
            this.falseJumper = falseJumper;
        }
    }
}
