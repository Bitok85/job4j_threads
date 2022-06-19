package ru.job4j.concurrent.producerconsumer;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    private static final int QUEUE_MAX_SIZE = 3;

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    public synchronized void offer(T value) {
        try {
            while (queue.size() == QUEUE_MAX_SIZE) {
                wait();
            }
            if (queue.size() == 0) {
                notifyAll();
            }
            queue.add(value);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    public synchronized T poll() {
        try {
            while (queue.size() == 0) {
                wait();
            }
            if (queue.size() == QUEUE_MAX_SIZE) {
                notifyAll();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return queue.poll();
    }

    public synchronized int size() {
        return queue.size();
    }

}
