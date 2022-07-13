package ru.job4j.concurrent;

/**
 * delete changes
 */

public class Cache {
    private static Cache cache;

    public static synchronized Cache instOf() {
        return cache == null ? new Cache() : cache;
    }
}


