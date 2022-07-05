package ru.job4j.concurrent.forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelLinearSearch<T> extends RecursiveTask<List<Integer>> {

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

    @Override
    protected List<Integer> compute() {
        if (to - from <= MIN_ARR_LENGTH) {
            List<Integer> tmpList = new ArrayList<>();
            for (int i = from; i < to; i++) {
                if (value.equals(array[i])) {
                    tmpList.add(i);
                }
            }
            return tmpList;
        }
        int mid = array.length / 2;
        ParallelLinearSearch<T> leftSearch = new ParallelLinearSearch<>(array, value, from, mid);
        ParallelLinearSearch<T> rightSearch = new ParallelLinearSearch<>(array, value, mid + 1, to);
        leftSearch.fork();
        rightSearch.fork();
        List<Integer> rsl = new ArrayList<>();
        rsl.addAll(leftSearch.join());
        rsl.addAll(rightSearch.join());
        return rsl;
    }

    public static void main(String[] args) {
        Integer[] arr = {1, 2, 3, 4, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 11, 18, 19, 20, 21};
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        System.out.println(forkJoinPool.invoke(
                new ParallelLinearSearch<Integer>(arr, 11, 0, arr.length - 1)));
    }
}
