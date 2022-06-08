package ru.job4j.concurrent;

public class Cache {
    private static Cache cache;

    public static synchronized Cache instOf() {
        return cache == null ? new Cache() : cache;
    }
}


