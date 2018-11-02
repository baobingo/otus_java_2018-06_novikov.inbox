package ru.otus.l131;

import java.util.Arrays;

public class MultithreadSort {

    public int[] sort(int[] a){

        int chunk = a.length/4;

        Thread th1 = new ThreadUnitSort(Arrays.copyOfRange(a, 0, chunk));
        Thread th2 = new ThreadUnitSort(Arrays.copyOfRange(a, chunk, chunk*2));
        Thread th3 = new ThreadUnitSort(Arrays.copyOfRange(a, chunk*2, chunk*3));
        Thread th4 = new ThreadUnitSort(Arrays.copyOfRange(a, chunk*3, a.length));
        th1.start();
        th2.start();
        th3.start();
        th4.start();
        try {
            th1.join();
            th2.join();
            th3.join();
            th4.join();
        }catch (InterruptedException e){
        }

        Runnable r1 = new MergeRunnable(((ThreadUnitSort) th1).getA(), ((ThreadUnitSort) th2).getA());
        Runnable r2 = new MergeRunnable(((ThreadUnitSort) th3).getA(), ((ThreadUnitSort) th4).getA());

        Thread th5 = new Thread(r1);
        Thread th6 = new Thread(r2);
        th5.start();
        th6.start();
        try {
            th5.join();
            th6.join();
        }catch (InterruptedException e){};

        Runnable r3 = new MergeRunnable(((MergeRunnable) r1).getResult(), ((MergeRunnable) r2).getResult());
        Thread th7 = new Thread(r3);
        th7.start();
        try {
            th7.join();
        }catch (InterruptedException e){};

        return ((MergeRunnable) r3).getResult();
    }
}
