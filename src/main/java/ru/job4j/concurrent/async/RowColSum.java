package ru.job4j.concurrent.async;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RowColSum {

    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }

        @Override
        public String toString() {
            return "Sums{"
                    + "rowSum="
                    + rowSum + ", colSum="
                    + colSum
                    + '}';
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = new Sums(0, 0);
            for (int j = 0; j < matrix.length; j++) {
                sums[i].setRowSum(sums[i].getRowSum() + matrix[i][j]);
                sums[i].setColSum(sums[i].getColSum() + matrix[j][i]);
            }
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] sums = new Sums[matrix.length];
        Map<Integer, CompletableFuture<Integer>> rowFutures = new HashMap<>();
        Map<Integer, CompletableFuture<Integer>> colFutures = new HashMap<>();
        for (int i = 0; i < matrix.length; i++) {
            rowFutures.put(i, getRowSum(matrix, i));
            colFutures.put(i, getColSum(matrix, i));
        }
        for (int j = 0; j < matrix.length; j++) {
            sums[j] = new Sums(0, 0);
            sums[j].setRowSum(rowFutures.get(j).get());
            sums[j].setColSum(colFutures.get(j).get());
        }
        return sums;
    }

    private static CompletableFuture<Integer> getRowSum(int[][] matrix, int row) {
        return CompletableFuture.supplyAsync(() -> {
            int sum = 0;
            for (int i = 0; i < matrix.length; i++) {
                sum += matrix[row][i];
            }
            return sum;
        });
    }

    private static CompletableFuture<Integer> getColSum(int[][] matrix, int col) {
        return CompletableFuture.supplyAsync(() -> {
            int sum = 0;
            for (int i = 0; i < matrix.length; i++) {
                sum += matrix[i][col];
            }
            return sum;
        });
    }

    public static int[][] generateQuadMatrix(int matrixLineSize) {
        int[][] rsl = new int[matrixLineSize][];
        Random random = new Random();
        for (int i = 0; i < matrixLineSize; i++) {
            rsl[i] = new int[matrixLineSize];
            for (int j = 0; j < matrixLineSize; j++) {
                rsl[i][j] = random.nextInt(100);
            }
        }
        return rsl;
    }

}
