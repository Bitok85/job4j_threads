package ru.job4j.concurrent;

import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class UserCache {

    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    public void add(User user) {
        users.put(id.incrementAndGet(), User.of(user.getName()));
    }

    public User findByID(int id) {
        return User.of(users.get(id).getName());
    }

    public List<User> findAll() {
        List<User> rsl = new ArrayList<>();
        users.forEach((k, v) -> rsl.add(User.of(v.getName())));
        return rsl;
    }
}
