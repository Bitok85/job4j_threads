package ru.job4j.concurrent.pool;

import ru.job4j.concurrent.producerconsumer.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private static final int BLOCKING_QUE_SIZE = 5;
    private static final int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private final List<Thread> treads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(BLOCKING_QUE_SIZE);


    public ThreadPool() {
        for (int i = 0; i < THREAD_POOL_SIZE; i++) {
            Thread thread = new Thread();
            thread.start();
            treads.add(thread);
        }
    }

    public void work(Runnable job) throws InterruptedException {
            tasks.offer(job);
    }

    public void shutDown() {
        treads.forEach(Thread::interrupt);
    }
}
