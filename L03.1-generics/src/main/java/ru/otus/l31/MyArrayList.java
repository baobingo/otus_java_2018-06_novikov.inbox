package ru.otus.l31;

import java.util.*;

public class MyArrayList<T> implements List<T> {

    private static final Object[] DEFAULT_SIZE = {};
    private int count;

    transient Object[] elementData;

    public MyArrayList(int size) {
        this.elementData = new Object[size];
    }

    public MyArrayList() {
        this.elementData = DEFAULT_SIZE;
    }

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    public Iterator<T> iterator() {
        return new MyListIterator<T>(this);
    }

    public Object[] toArray() {
        return Arrays.copyOf(elementData, size());
    }

    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException();
    }

    public boolean add(T t) {
        add(count, t);
        return true;
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }

    public T get(int index){
        return (T)elementData[index];
    }

    public T set(int index, T element) {
        try {
            T oldT = (T) elementData[index];
            elementData[index] = element;
            return oldT;
        }catch (ArrayIndexOutOfBoundsException e){
            throw new IndexOutOfBoundsException();
        }
    }

    public void add(int index, T element) {
        final int s;
        if((s=count)==elementData.length)
            elementData = Arrays.copyOf(elementData,
                    elementData.length + (elementData.length>>1));
        if(index<count)
            System.arraycopy(elementData, index,
                elementData, index + 1,
                s - index);
        elementData[index] = element;
        count++;
    }

    public T remove(int index) {
        throw new UnsupportedOperationException();
    }

    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    public ListIterator<T> listIterator() {
        return new MyListIterator<T>(this);
    }

    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

}
