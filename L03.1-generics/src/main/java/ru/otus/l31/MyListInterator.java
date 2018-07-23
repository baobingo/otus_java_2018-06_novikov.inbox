package ru.otus.l31;

import java.util.ListIterator;
import java.util.NoSuchElementException;

class MyListInterator<T> implements ListIterator<T> {

    private int currentIndex = 0;
    transient MyArrayList<T> elementData;

    public MyListInterator(MyArrayList<T> elementData) {
        this.elementData = elementData;
    }

    @Override
    public boolean hasNext() {
        try{
            elementData.get(currentIndex);
        }catch (IndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }

    @Override
    public T next() {
        try {
            T element = elementData.get(currentIndex);
            currentIndex++;
            return element;
        }catch (ArrayIndexOutOfBoundsException e){
            throw new NoSuchElementException();
        }
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
     throw new UnsupportedOperationException(); //:)
    }

    @Override
    public void set(T t) {
        elementData.add(currentIndex,t);
    }

    @Override
    public void add(T t) {
        elementData.add(t);
    }
}