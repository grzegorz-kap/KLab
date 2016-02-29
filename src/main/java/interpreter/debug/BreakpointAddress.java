package interpreter.debug;

import java.util.Objects;

public class BreakpointAddress {
    private Integer line;

    public BreakpointAddress(Integer line) {
        this.line = Objects.requireNonNull(line);
    }

    @Override
    public boolean equals(Object obj) {
        BreakpointAddress s = obj instanceof BreakpointAddress ? ((BreakpointAddress) obj) : null;
        return s != null && s.line.equals(this.line);
    }

    @Override
    public int hashCode() {
        return line.hashCode();
    }

    @Override
    public String toString() {
        return String.format("line: %d", line);
    }

    public Integer getLine() {
        return line;
    }
}
