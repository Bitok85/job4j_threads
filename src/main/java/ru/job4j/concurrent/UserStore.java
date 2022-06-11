package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Objects;

@ThreadSafe
final public class UserStore {

    @GuardedBy("this")
    private final int id;
    @GuardedBy("this")
    private int amount;

    public UserStore(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public synchronized int getId() {
        return id;
    }

    public synchronized int getAmount() {
        return amount;
    }

    public synchronized void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserStore userStore = (UserStore) o;
        return id == userStore.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
