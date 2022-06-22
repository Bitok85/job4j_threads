package ru.job4j.concurrent.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class Cache {

    private static final int VERSION_DELTA = 1;
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        AtomicBoolean result = new AtomicBoolean(false);
        memory.computeIfPresent(model.getId(), (k, v) -> {
            Base stored = memory.get(model.getId());
            if (stored.getVersion() != model.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            v.setVersion(v.getVersion() + VERSION_DELTA);
            result.set(true);
            return v;
        });
        return result.get();
    }

    public boolean delete(Base model) {
        return memory.remove(model.getId()) != null;
    }
}
