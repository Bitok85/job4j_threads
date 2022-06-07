package ru.job4j.concurrent;

public class Cache {
    private static Cache cache;

    public static Cache instOf() {
        return cache == null ? new Cache() : cache;
    }
}


