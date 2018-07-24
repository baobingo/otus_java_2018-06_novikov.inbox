package ru.otus.l31;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class MyArrayListTest {

    static MyArrayList<Integer> myListIterator;

    public MyArrayListTest() {
        myListIterator = new MyArrayList<Integer>(12);
        Integer arrayInt[] = new Integer[12];
        for (int i = 0; i < arrayInt.length; i++) {
            arrayInt[i] = i;
        }
        Collections.addAll(myListIterator, arrayInt);
    }

    @Test
    void size() {
        assertEquals(myListIterator.size(), 12);
    }

    @Test
    void get() {
        assertEquals(myListIterator.get(myListIterator.size()-1), (Integer)11);
    }

    @Test
    void set() {
        Integer oldValue = myListIterator.set(1,22);
        assertEquals(oldValue, (Integer)1);
        assertEquals(myListIterator.get(1), (Integer)22);
    }

    @Test
    void add() {
        myListIterator.add(0,77);
        assertEquals(myListIterator.get(0), (Integer)77);
        assertEquals(myListIterator.size(), 13);
    }
}