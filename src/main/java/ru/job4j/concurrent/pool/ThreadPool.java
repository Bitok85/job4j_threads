package ru.job4j.concurrent.pool;

import ru.job4j.concurrent.producerconsumer.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

class ThreadPool {
    private static final int BLOCKING_QUE_SIZE = 5;
    private static final int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(BLOCKING_QUE_SIZE);



    public ThreadPool() {
        for (int i = 0; i < THREAD_POOL_SIZE; i++) {
            Thread thread = new Thread(
                    () -> {
                        while (!Thread.currentThread().isInterrupted()) {
                            try {
                                Runnable task = tasks.poll();
                                task.run();
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                e.printStackTrace();
                            }
                        }
                    }
            );
            threads.add(thread);
            thread.start();
        }
    }

    public synchronized void work(Runnable job) throws InterruptedException {
            tasks.offer(job);
    }

    public void shutDown() {
        threads.forEach(Thread::interrupt);
    }

    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool();
        try  {
            threadPool.work(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Execute " + Thread.currentThread().getName());
                }
            });
            threadPool.work(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Execute " + Thread.currentThread().getName());
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadPool.shutDown();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName());
    }
}
