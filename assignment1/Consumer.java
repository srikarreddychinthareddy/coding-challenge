package assignment1;

import java.util.List;


public class Consumer implements Runnable {
    private final Buffer<Integer> buffer;
    private final List<Integer> destination;
    private final Integer sentinel;


    public Consumer(Buffer<Integer> buffer, List<Integer> destination, Integer sentinel) {
        this.buffer = buffer;
        this.destination = destination;
        this.sentinel = sentinel;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Integer item = buffer.take();
                if (item == null || item.equals(sentinel)) {
                    break;
                }
                destination.add(item);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}