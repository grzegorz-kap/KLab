package interpreter.types;

public class Timer implements ObjectData {
    private Long counter;

    public void start() {
        counter = System.nanoTime();
    }

    public Long stop() {
        Long interval = System.nanoTime() - counter;
        counter = null;
        return interval;
    }
}
