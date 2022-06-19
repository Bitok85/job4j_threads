package ru.job4j.concurrent.producerconsumer;

import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    @Ignore
    @Test
    public void whenProduceAndConsumeFewTimes() {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        List<Integer> source = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<Integer> destination = new ArrayList<>();
        Thread pThread = new Thread(() -> new Producer<>(source, queue));
        Thread cThread = new Thread(() -> new Consumer<>(queue, destination));
        try {
            pThread.start();
            pThread.join();
            cThread.start();
            cThread.join();
        }  catch (Exception e) {
            e.printStackTrace();
        }
        assertThat(destination.size(), is(9));

    }

}