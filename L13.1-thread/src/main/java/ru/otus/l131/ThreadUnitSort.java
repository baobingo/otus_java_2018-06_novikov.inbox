package ru.otus.l131;

import java.util.Arrays;

public class ThreadUnitSort implements Runnable{
    private int[] a;

    public ThreadUnitSort(int[] a) {
        this.a = a;
    }

    @Override
    public void run() {
        Arrays.sort(a);
    }
}
