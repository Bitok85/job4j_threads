package ru.job4j.concurrent.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelLinearSearch<T> extends RecursiveTask<Integer> {

    private static final int MIN_ARR_LENGTH = 10;

    private final T[] array;
    private final T value;
    private final int from;
    private final int to;

    public ParallelLinearSearch(T[] array, T value, int from, int to) {
        this.array = array;
        this.value = value;
        this.from = from;
        this.to = to;
    }

    public static <T> Integer searchIndex(T[] array, T value, int from, int to) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelLinearSearch<>(array, value, from, to));
    }

    @Override
    protected Integer compute() {
        if (to - from <= MIN_ARR_LENGTH) {
            return valueCheck(array, from, to);
        }
        int mid = (to + from) / 2;
        ParallelLinearSearch<T> leftSearch = new ParallelLinearSearch<>(array, value, from, mid);
        ParallelLinearSearch<T> rightSearch = new ParallelLinearSearch<>(array, value, mid + 1, to);
        leftSearch.fork();
        rightSearch.fork();
        return resultChoice(leftSearch.join(), rightSearch.join());

    }

    private Integer valueCheck(T[] array, int from, int to) {
        for (int i = from; i <= to; i++) {
            if (value.equals(array[i])) {
                return i;
            }
        }
        return -1;
    }


    private static int resultChoice(Integer left, Integer right) {
        int rsl = -1;
        if (left != null) {
            rsl = left;
        }
        if (right != null) {
            rsl = right;
        }
        return rsl;
    }

}
