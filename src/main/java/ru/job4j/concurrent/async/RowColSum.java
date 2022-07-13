package ru.job4j.concurrent.async;

import java.util.*;
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
        Map<Integer, CompletableFuture<Sums>> mapSums = new HashMap<>();
        for (int i = 0; i < matrix.length; i++) {
            mapSums.put(i, getSum(matrix, i));
        }
        for (int j = 0; j < matrix.length; j++) {
            sums[j] = mapSums.get(j).get();
        }
        return sums;
    }

    private static CompletableFuture<Sums> getSum(int[][] matrix, int index) {
        return CompletableFuture.supplyAsync(() -> {
            Sums sum = new Sums(0, 0);
            for (int i = 0; i < matrix.length; i++) {
                sum.setRowSum(sum.getRowSum() + matrix[index][i]);
                sum.setColSum(sum.getColSum() + matrix[i][index]);
            }
            return sum;
        });
    }


    public static int[][] generateQuadMatrix(int lineSize) {
        int[][] rsl = new int[lineSize][];
        Random random = new Random();
        for (int i = 0; i < lineSize; i++) {
            rsl[i] = new int[lineSize];
            for (int j = 0; j < lineSize; j++) {
                rsl[i][j] = random.nextInt(100);
            }
        }
        return rsl;
    }

}
