package ru.job4j.concurrent.producerconsumer;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final int queueSize;
    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    public SimpleBlockingQueue(int queueSize) {
        this.queueSize = queueSize;
    }

    public synchronized void offer(T value) throws InterruptedException {
        queue.add(value);
        notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
            T rsl = queue.poll();
            notifyAll();
        return rsl;
    }

    public synchronized int size() {
        return queue.size();
    }

}
