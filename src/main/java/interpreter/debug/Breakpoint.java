package interpreter.debug;

public class Breakpoint {
    private String sourceId;
    private BreakpointAddress address;

    public Breakpoint(String sourceId, Integer lineNumber) {
        this.sourceId = sourceId;
        this.address = new BreakpointAddress(lineNumber);
    }

    @Override
    public boolean equals(Object obj) {
        Breakpoint s = obj instanceof Breakpoint ? ((Breakpoint) obj) : null;
        return s != null && address.equals(s.address) && sourceId.equals(s.sourceId);
    }

    @Override
    public int hashCode() {
        return address.hashCode();
    }

    public String getSourceId() {
        return sourceId;
    }

    public BreakpointAddress getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return String.format("Breakpoint: '%s', %s", sourceId, address);
    }
}
