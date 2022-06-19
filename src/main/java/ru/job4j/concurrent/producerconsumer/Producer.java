package ru.job4j.concurrent.producerconsumer;

import java.util.List;

public class Producer<T> implements Runnable {

    private final SimpleBlockingQueue<T> queue;
    private final List<T> source;
    private int produceIndex = 0;

    public Producer(List<T> source, SimpleBlockingQueue<T> queue) {
        this.source = source;
        this.queue = queue;
    }

    @Override
    public void run() {
        while (produceIndex < source.size()) {
            queue.offer(produce());
        }
    }

    private T produce() {
        if (produceIndex == source.size()) {
            produceIndex = 0;
        }
        T rsl = source.get(produceIndex);
        produceIndex++;
        return  rsl;
    }
}
