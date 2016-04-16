package com.klab.interpreter.translate.keyword.model;

import com.klab.interpreter.translate.model.JumperInstruction;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

public class ForInstructionContext {
    private Deque<ForIterator> iterators = new ArrayDeque<>();

    public void push(int flhnextAddress, JumperInstruction falseJumper) {
        iterators.addFirst(new ForIterator(flhnextAddress, falseJumper));
    }

    public void addFalseJumper(JumperInstruction falseJumper) {
        iterators.peekFirst().falseJumpers.add(falseJumper);
    }

    public String getName() {
        return iterators.peekFirst().name;
    }

    public void setName(String name) {
        iterators.peekFirst().name = name;
    }

    public int getFlhNextAddress() {
        return iterators.peekFirst().flhnextAddress;
    }

    public void pop() {
        iterators.removeFirst();
    }

    public int size() {
        return iterators.size();
    }

    public void setJumpsOnFalse(int i) {
        iterators.peekFirst().falseJumpers.forEach(jmp -> jmp.setJumpIndex(i));
    }

    private static class ForIterator {
        public int flhnextAddress;
        public Set<JumperInstruction> falseJumpers = new HashSet<>();
        public String name;

        public ForIterator(int flhnextAddress, JumperInstruction falseJumper) {
            this.flhnextAddress = flhnextAddress;
            falseJumpers.add(falseJumper);
        }
    }
}
