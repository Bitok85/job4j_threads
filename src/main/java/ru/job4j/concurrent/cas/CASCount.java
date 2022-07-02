package ru.job4j.concurrent.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;


@ThreadSafe
public class CASCount {

    private final AtomicInteger count = new AtomicInteger(0);

    public CASCount(int startValue) {
        count.set(startValue);
    }

    public void increment() {
        int value;
        do {
            value = count.get();
        } while (!count.compareAndSet(value, value + 1));
    }

    public int get() {
        return count.get();
    }

}
