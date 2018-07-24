package ru.otus.l31;

import java.util.ListIterator;
import java.util.NoSuchElementException;

class MyListIterator<T> implements ListIterator<T> {

    private int currentIndex;
    private int lastGotIndex = -1; //for seq next set
    transient MyArrayList<T> elementData;

    public MyListIterator(MyArrayList<T> elementData) {
        this.elementData = elementData;
    }

    @Override
    public boolean hasNext() {
        return currentIndex!=elementData.size();
    }

    @Override
    public T next() {
        if(currentIndex>=elementData.size())
            throw new NoSuchElementException();

        T element = elementData.get(currentIndex);
        lastGotIndex = currentIndex;
        currentIndex++;
        return element;
    }

    @Override
    public boolean hasPrevious() {
        try{
            elementData.get(currentIndex-1);
        }catch (IndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }

    @Override
    public T previous() {
        try {
            T element = elementData.get(currentIndex-1);
            currentIndex--;
            return element;
        }catch (ArrayIndexOutOfBoundsException e){
            throw new NoSuchElementException();
        }
    }

    @Override
    public int nextIndex() {
        if(currentIndex==elementData.size())
            return -1;
        return currentIndex;
    }

    @Override
    public int previousIndex() {
        if(currentIndex==0)
            return currentIndex-1;
        if(currentIndex==elementData.size())
            return currentIndex-2;
        return currentIndex-1;
    }

    @Override
    public void remove() {
     throw new UnsupportedOperationException();
    }

    @Override
    public void set(T t) {
        elementData.set(lastGotIndex,t);
    }

    @Override
    public void add(T t) {
        elementData.add(t);
    }
}