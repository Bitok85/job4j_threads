package ru.job4j.concurrent.cas;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class CASCountTest {

    @Test
    public void whenFewIncrementsAndThenGet() {
        CASCount casCount = new CASCount(0);
        for (int i = 0; i < 130; i++) {
            casCount.increment();
        }
        assertThat(casCount.get(), is(130));
    }

    @Test(timeout = 3000)
    public void whenTwoThreadsUsingIncrementInTurn() throws InterruptedException {
        CASCount casCount = new CASCount(0);
        Thread thread1 = new Thread(
                () -> {
                    for (int i = 0; i < 1000; i++) {
                        casCount.increment();
                    }
                }
        );
        Thread thread2 = new Thread(
                () -> {
                    for (int i = 0; i < 1000; i++) {
                        casCount.increment();
                    }
                }

        );
        thread1.start();
        thread1.join();
        thread2.start();
        thread2.join();
        assertEquals(casCount.get(), 2000);
    }
}