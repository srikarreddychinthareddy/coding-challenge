package assignment1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class ProducerConsumerTest {

    private List<Integer> runProducerConsumer(List<Integer> source, int capacity) {
        Buffer<Integer> buffer = new Buffer<>(capacity);
        List<Integer> destination = new ArrayList<>();
        Integer sentinel = null;
        Thread producerThread = new Thread(new Producer(source, buffer, sentinel));
        Thread consumerThread = new Thread(new Consumer(buffer, destination, sentinel));
        producerThread.start();
        consumerThread.start();
        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return destination;
    }

    @Test
    public void testItemsConsumedInOrder() {
        List<Integer> source = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> dest = runProducerConsumer(source, 3);
        Assertions.assertEquals(source, dest);
    }

    @Test
    public void testEmptySource() {
        List<Integer> source = new ArrayList<>();
        List<Integer> dest = runProducerConsumer(source, 2);
        Assertions.assertTrue(dest.isEmpty());
    }
}