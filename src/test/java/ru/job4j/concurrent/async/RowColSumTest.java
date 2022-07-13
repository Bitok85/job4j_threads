package ru.job4j.concurrent.async;

import org.junit.Ignore;
import org.junit.Test;

import javax.rmi.ssl.SslRMIClientSocketFactory;

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
                new RowColSum.Sums(6, 12),
                new RowColSum.Sums(15,  15),
                new RowColSum.Sums(24, 18)
        };
        final long startTime = System.currentTimeMillis();
        assertArrayEquals(expected, RowColSum.sum(matrix));
        System.out.println(System.currentTimeMillis() - startTime);
    }

    @Test
    public void whenAsyncExecution() throws Exception {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        RowColSum.Sums[] expected = {
                new RowColSum.Sums(6, 12),
                new RowColSum.Sums(15, 15),
                new RowColSum.Sums(24, 18)
        };
        assertArrayEquals(expected, RowColSum.asyncSum(matrix));
    }

    @Ignore
    @Test
    public void whenBigMatrixThenAsyncRunFaster() throws Exception {
        int[][] matrix = RowColSum.generateQuadMatrix(10000);

        final long startSeqTime = System.currentTimeMillis();
        RowColSum.Sums[] sums1 = RowColSum.sum(matrix);
        final long seqTimeRsl = System.currentTimeMillis() - startSeqTime;

        final long startAsyncTime = System.currentTimeMillis();
        RowColSum.Sums[] sums2 = RowColSum.asyncSum(matrix);
        final long asyncTimeRsl = System.currentTimeMillis() - startAsyncTime;

        System.out.println(seqTimeRsl);
        System.out.println(asyncTimeRsl);

        assertTrue(seqTimeRsl > asyncTimeRsl);
    }

}