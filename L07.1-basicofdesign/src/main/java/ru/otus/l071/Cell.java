package ru.otus.l071;

public interface Cell {
    boolean put(int count);
    boolean get(int count);
    int getCurrentCount();
    int getFreeCount();
}