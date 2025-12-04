package assignment1;

import java.util.LinkedList;
import java.util.List;

public class Buffer<T> {
    private final List<T> queue = new LinkedList<>();
    private final int capacity;

    public Buffer(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("capacity must be at least 1");
        }
        this.capacity = capacity;
    }


    public synchronized void put(T item) throws InterruptedException {
        while (queue.size() >= capacity) {
            wait();
        }
        queue.add(item);
        notifyAll();
    }


    public synchronized T take() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        T item = queue.remove(0);
        notifyAll();
        return item;
    }
}