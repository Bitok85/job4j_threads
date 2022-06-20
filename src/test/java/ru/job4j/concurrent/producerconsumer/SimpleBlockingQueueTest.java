package ru.job4j.concurrent.producerconsumer;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    @Test
    public void whenProduceAndConsumeFewTimes() throws InterruptedException {
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        List<Integer> source = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Thread producer = new Thread(
                () -> {
                    try {
                        for (Integer integer : source) {
                            queue.offer(integer);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer, is(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9)));
    }

    @Test
    public void whenTwoProducersAndOneConsumer() throws InterruptedException {
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        Thread firstProd = new Thread(
                () -> {
                    try {
                        for (int i = 1; i <= 6; i++) {
                            queue.offer(i);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                }
        );
        Thread secondProd = new Thread(
                () -> {
                    try {
                        for (int i = 7; i <= 12; i++) {
                            queue.offer(i);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                }
        );
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        firstProd.start();
        secondProd.start();
        consumer.start();
        firstProd.join();
        secondProd.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer.size(), is(12));
    }
}