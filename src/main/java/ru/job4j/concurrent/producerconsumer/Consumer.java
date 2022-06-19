package ru.job4j.concurrent.producerconsumer;

import java.util.List;

public class Consumer<T> implements Runnable {

    private final SimpleBlockingQueue<T> queue;
    private final List<T> destination;

    public Consumer(SimpleBlockingQueue<T> queue, List<T> destination) {
        this.queue = queue;
        this.destination = destination;
    }

    @Override
    public void run() {
        while (queue.size() > 0) {
            destination.add(queue.poll());
        }
    }
}
