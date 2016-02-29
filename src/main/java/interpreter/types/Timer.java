package interpreter.types;

public class Timer implements ObjectData {
    private Long counter;
    private String name;

    public void start() {
        counter = System.nanoTime();
    }

    public Long stop() {
        Long interval = System.nanoTime() - counter;
        counter = null;
        return interval / 1_000_000L;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
