package ru.otus.l131;

public class MergeRunnable implements Runnable {
    private int[] result;
    private int[] a;
    private int[] b;

    public MergeRunnable(int[] a, int[] b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public void run() {
        result = mergeSortedArrays(a, b);
    }

    public int[] getResult() {
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
