package ru.job4j.concurrent.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {

private final AtomicReference<Integer> count = new AtomicReference<>();
private int value;

    public void increment() {
        count.set(value);
        count.compareAndSet(value, value++);
    }

    public int get() {
        return count.get();
}

}
