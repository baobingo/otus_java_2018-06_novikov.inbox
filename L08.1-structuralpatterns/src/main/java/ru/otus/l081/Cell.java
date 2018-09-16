package ru.otus.l081;

public interface Cell {
    boolean put(int count);
    boolean get(int count);
    int getCurrentCount();
    int getFreeCount();
}