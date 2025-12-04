package assignment1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ProducerConsumerDemo {
    public static void main(String[] args) {
        List<Integer> source = Arrays.asList(1, 2, 3, 4, 5);
        Buffer<Integer> buffer = new Buffer<>(3);
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
        System.out.println("Source: " + source);
        System.out.println("Destination: " + destination);
    }
}