package ru.job4j.concurrent.pool;

import ru.job4j.concurrent.producerconsumer.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

class ThreadPool {
    private static final int BLOCKING_QUE_SIZE = 5;
    private static final int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private final List<Thread> treads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(BLOCKING_QUE_SIZE);


    public ThreadPool() {
      for (int i = 0; i < THREAD_POOL_SIZE; i++) {
          treads.add(new Thread(
                  () -> {
                      try {
                          while (!tasks.isEmpty()) {
                              Runnable task = tasks.poll();
                              task.run();
                          }
                      } catch (InterruptedException e) {
                          e.printStackTrace();
                      }
                  }
          ));
      }
    }

    public synchronized void work(Runnable job) throws InterruptedException {
            tasks.offer(job);
    }

    public void shutDown() {
        treads.forEach(Thread::interrupt);
    }
}
