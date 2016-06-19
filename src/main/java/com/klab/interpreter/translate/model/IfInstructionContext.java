package com.klab.interpreter.translate.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.function.Consumer;

public class IfInstructionContext {

    private Deque<IfContext> ifContexts = new ArrayDeque<>();

    public void addIf() {
        ifContexts.push(new IfContext());
    }

    public void removeLastIf() {
        ifContexts.pop();
    }

    public void addEndIfJumper(JumperInstruction jumperInstruction) {
        ifContexts.peek().endIfJumps.add(jumperInstruction);
    }

    public void forEachEndIfJumper(Consumer<? super JumperInstruction> action) {
        ifContexts.peek().endIfJumps.forEach(action);
    }

    public JumperInstruction getJumpOnFalse() {
        return ifContexts.peek().jumpOnFalse;
    }

    public void setJumpOnFalse(JumperInstruction jumperInstruction) {
        ifContexts.peek().jumpOnFalse = jumperInstruction;
    }

    public int size() {
        return ifContexts.size();
    }

    private static class IfContext {
        public List<JumperInstruction> endIfJumps = new ArrayList<>();
        public JumperInstruction jumpOnFalse;
    }
}
