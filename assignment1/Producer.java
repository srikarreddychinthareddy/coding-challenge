package assignment1;

import java.util.List;


public class Producer implements Runnable {
    private final List<Integer> source;
    private final Buffer<Integer> buffer;
    private final Integer sentinel;


    public Producer(List<Integer> source, Buffer<Integer> buffer, Integer sentinel) {
        this.source = source;
        this.buffer = buffer;
        this.sentinel = sentinel;
    }

    @Override
    public void run() {
        try {
            for (Integer item : source) {
                buffer.put(item);
            }
            // insert sentinel to signal completion
            buffer.put(sentinel);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}