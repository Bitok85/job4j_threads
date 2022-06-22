package ru.job4j.concurrent.cache;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class CacheTest {

    @Test
    public void whenCacheAddThenTrue() {
        Cache cache = new Cache();
        Base model = new Base(1, 1);
        assertTrue(cache.add(model));
    }

    @Test
    public void whenCacheDeleteThenTrue() {
        Cache cache = new Cache();
        Base model = new Base(1, 1);
        cache.add(model);
        assertTrue(cache.delete(model));
    }

    @Test
    public void whenModelInCacheUpdatesThenVersionIncrementsByOne() {
        Cache cache = new Cache();
        Base model = new Base(1, 1);
        cache.add(model);
        assertTrue(cache.update(model));
        assertEquals(model.getVersion(), 2);
    }

    @Test (expected = OptimisticException.class)
    public void whenVersionsAreNotEqualWhileUpdateThenExceptionAndFalse() {
        Cache cache = new Cache();
        Base model1 = new Base(1, 1);
        Base model2 = new Base(1, 2);
        cache.add(model1);
        assertFalse(cache.update(model2));
    }

}