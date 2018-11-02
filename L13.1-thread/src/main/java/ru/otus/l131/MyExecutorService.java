package ru.otus.l131;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MyExecutorService {
    private int[] result;

    public int[] sort(int[] a) throws InterruptedException{
        int chunk = a.length/4;

        ExecutorService service = Executors.newFixedThreadPool(4);
        Future<int[]> a1 = service.submit(new SortCallable(Arrays.copyOfRange(a, 0, chunk)));
        Future<int[]> a2 = service.submit(new SortCallable(Arrays.copyOfRange(a, chunk, chunk*2)));
        Future<int[]> a3 = service.submit(new SortCallable(Arrays.copyOfRange(a, chunk*2, chunk*3)));
        Future<int[]> a4 = service.submit(new SortCallable(Arrays.copyOfRange(a, chunk*3, a.length)));

        while(!a1.isDone()&&!a2.isDone()&&!a3.isDone()&&!a4.isDone()){}

        try {
            Future<int[]> merge1 = service.submit(new MergeCallable(a1.get(), a2.get()));
            Future<int[]> merge2 = service.submit(new MergeCallable(a3.get(), a4.get()));

            while (!merge1.isDone()&&!merge2.isDone()){}

            Future<int[]> merge3 = service.submit(new MergeCallable(merge1.get(), merge2.get()));

            while(!merge3.isDone()){}

            result = merge3.get();

        }catch (InterruptedException | ExecutionException e){}


        if(result==null){
            throw new InterruptedException();
        }
        return result;
    }
}
