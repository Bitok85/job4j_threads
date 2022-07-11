package ru.job4j.concurrent.forkjoin;

import org.junit.Test;

import static org.junit.Assert.*;

public class ParallelLinearSearchTest {


    @Test
    public void whenFindIndexOfExistingWithParallelSearch() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
        int expected = 21;
        int rsl = ParallelLinearSearch.searchIndex(array, 22, 0, array.length - 1);
        assertEquals(expected, rsl);
    }

    @Test
    public void whenFindIndexOfNotExistingValueThenMinusOne() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
        int expected = -1;
        int rsl = ParallelLinearSearch.searchIndex(array, 30, 0, array.length - 1);
        assertEquals(expected, rsl);
    }

}