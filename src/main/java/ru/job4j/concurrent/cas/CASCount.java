package ru.job4j.concurrent.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {

private final AtomicReference<Integer> count = new AtomicReference<>();

    public void increment() {
        int delta = 1;
        if (count.get() == null) {
            count.set(delta);
            return;
        }
        int value = count.get();
        int newValue = value + delta;
        do {
            count.set(newValue);
        } while (count.compareAndSet(value, newValue));
    }

    public int get() {
        return count.get();
    }
}
