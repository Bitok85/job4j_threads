package ru.job4j.concurrent.async;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class RowColSumTest {

    @Test
    public void whenSequentialExecution() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        RowColSum.Sums[] expected = {
                new RowColSum.Sums(6 ,12),
                new RowColSum.Sums(15, 15),
                new RowColSum.Sums(24, 18)
        };
        System.out.println();
        assertArrayEquals(expected, RowColSum.sum(matrix));
    }

}