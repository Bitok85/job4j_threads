package ru.job4j.concurrent.cas;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {

private final AtomicReference<Integer> count = new AtomicReference<>();

    public CASCount(int startValue) {
        count.set(startValue);
    }

    public void increment() {
        AtomicInteger atomicInteger = new AtomicInteger(count.get());
        int value;
        do {
            value = atomicInteger.intValue();
        } while (count.compareAndSet(value, value + 1));
    }

    public int get() {
        return count.get();
    }
}

