package ru.otus.l131;

import java.util.Arrays;

public class MultithreadSort {

    private int[] result;
    private int[] tmp1;
    private int[] tmp2;

    public int[] sort(int[] a){

        int chunk = a.length/4;

        int[] a1 = Arrays.copyOfRange(a, 0, chunk);
        int[] a2 = Arrays.copyOfRange(a, chunk, chunk*2);
        int[] a3 = Arrays.copyOfRange(a, chunk*2, chunk*3);
        int[] a4 = Arrays.copyOfRange(a, chunk*3, a.length);

        Thread th1 = new Thread(new ThreadUnitSort(a1));
        Thread th2 = new Thread(new ThreadUnitSort(a2));
        Thread th3 = new Thread(new ThreadUnitSort(a3));
        Thread th4 = new Thread(new ThreadUnitSort(a4));
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

        Thread th5 = new Thread(()->{tmp1 = mergeSortedArrays(a1, a2);});
        Thread th6 = new Thread(()->{tmp2 = mergeSortedArrays(a3, a4);});
        th5.start();
        th6.start();
        try {
            th5.join();
            th6.join();
        }catch (InterruptedException e){};
        result = mergeSortedArrays(tmp1, tmp2);

        return result;
    }

    //Input arrays must be sorted before
    private int [] mergeSortedArrays(int [] a, int [] b){
        int [] r = new int[a.length+b.length];
        int c = 0, ac = 0, ab = 0;
        while (ac < a.length && ab < b.length){
            if (a[ac] < b[ab]){
                r[c] = a[ac];
                c++; ac++;
            }else {
                r[c] = b[ab];
                c++; ab++;
            }
        }
        for(int index=ac; index<a.length; index++){
            r[c] = a[index];
            c++;
        }
        for(int index=ab; index<b.length; index++){
            r[c] = b[index];
            c++;
        }
        return r;
    }
}
