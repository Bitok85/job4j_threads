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
        Base result = memory.computeIfPresent(model.getId(), (id, base) -> {
            if (base.getVersion() != model.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            base.setVersion(base.getVersion() + VERSION_DELTA);
            return base;
        });
        return result != null;
    }

    public boolean delete(Base model) {
        return memory.remove(model.getId()) != null;
    }

}
