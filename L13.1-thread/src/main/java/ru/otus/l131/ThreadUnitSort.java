package ru.otus.l131;

import java.util.Arrays;

public class ThreadUnitSort extends Thread {
    private int[] a;
    private Runnable r;

    public ThreadUnitSort(int[] a) {
        this.a = a;
    }

    @Override
    public void run() {
        Arrays.sort(a);
    }

    public int[] getA() {
        return a;
    }
}
