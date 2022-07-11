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
            return valueCheck();
        }
        int mid = (to + from) / 2;
        ParallelLinearSearch<T> leftSearch = new ParallelLinearSearch<>(array, value, from, mid);
        ParallelLinearSearch<T> rightSearch = new ParallelLinearSearch<>(array, value, mid + 1, to);
        leftSearch.fork();
        rightSearch.fork();
        return Math.max(leftSearch.join(), rightSearch.join());

    }

    private int valueCheck() {
        for (int i = from; i <= to; i++) {
            if (value.equals(array[i])) {
                return i;
            }
        }
        return -1;
    }
}
