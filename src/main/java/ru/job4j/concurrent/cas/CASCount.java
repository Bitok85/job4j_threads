package ru.job4j.concurrent.cas;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {

private final AtomicReference<AtomicInteger> count = new AtomicReference<>();

    public CASCount(AtomicInteger startValue) {
        count.set(startValue);
    }
    private AtomicInteger atomicInteger = new AtomicInteger();

    public void increment() {
        do {
            atomicInteger = count.get();
        } while (count.compareAndSet(atomicInteger, new AtomicInteger(atomicInteger.get() + 1)));

    }

    public AtomicInteger get() {
        return count.get();
    }
}

