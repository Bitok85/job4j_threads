package ru.job4j.concurrent.pool;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public final class User {

    @GuardedBy("this")
    private final String name;
    @GuardedBy("this")
    private final String mail;

    public User(String name, String mail) {
        this.name = name;
        this.mail = mail;
    }

    public synchronized String getName() {
        return name;
    }

    public synchronized String getMail() {
        return mail;
    }
}
