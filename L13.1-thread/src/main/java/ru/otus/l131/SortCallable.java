package ru.otus.l131;

import java.util.Arrays;
import java.util.concurrent.Callable;

public class SortCallable implements Callable<int[]> {

    private int[] a;

    public SortCallable(int[] a) {
        this.a = a;
    }

    @Override
    public int[] call() throws Exception {
        Arrays.sort(a);
        return a;
    }
}
