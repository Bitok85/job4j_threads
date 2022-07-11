package ru.job4j.concurrent.async;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

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

    public static Sums[] asyncSum(int[][] matrix) {
        return null;
    }

    private static CompletableFuture<Integer> getTask(int[][] matrix) {
        return null;
    }
}
