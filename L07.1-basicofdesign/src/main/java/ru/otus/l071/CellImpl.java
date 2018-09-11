package ru.otus.l071;

public class CellImpl implements Cell {
    static final int maxCount = 1000;
    private int currentCount = 0;

    @Override
    public boolean put(int count) {
        currentCount+=count;
        return true;
    }

    @Override
    public boolean get(int count){
        if(currentCount>=count){
            currentCount-=count;
            return true;
        }
        return false;
    }

    @Override
    public int getCurrentCount() {
        return this.currentCount;
    }

    @Override
    public int getFreeCount() {
        return this.maxCount-this.currentCount;
    }

}
