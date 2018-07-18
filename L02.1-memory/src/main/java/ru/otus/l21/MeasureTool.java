package ru.otus.l21;


public class MeasureTool {
    private static long lastMem;
    private static long refSize;

    static long getLastMem() {
        return lastMem;
    }

    static void setLastMem(long lastMem) {
        MeasureTool.lastMem = lastMem;
    }

    static void calculateCurrentRefSize() throws InterruptedException{
        long m1 = getMem();
        Object[] objects = new Object[10_000_000];
        long m2 = getMem();
        refSize = (m2-m1)/objects.length;
        objects = null;
    }

    static long getRefSize() {
        return refSize;
    }

    static void makeMeasure(Object[] instance) throws InterruptedException {
        calculateCurrentRefSize();
        long currentUsage = getMem();
        System.out.println("Object size: " + (((currentUsage - getLastMem())/instance.length)-getRefSize()));
        instance = null;
        Thread.sleep(1000);
        setLastMem(getMem());
    }

    static long getMem() throws InterruptedException {
        System.gc();
        Thread.sleep(10);
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }
}


