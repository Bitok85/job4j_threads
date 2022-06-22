package ru.job4j.concurrent.cas;

import org.junit.Test;

import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class CASCountTest {

    @Test
    public void whenFewIncrementsAndThenGet() {
        CASCount casCount = new CASCount();
        for (int i = 0; i < 10; i++) {
            casCount.increment();
        }
        assertThat(casCount.get(), is(10));
    }
}