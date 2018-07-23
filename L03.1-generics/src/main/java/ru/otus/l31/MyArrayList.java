package ru.otus.l31;

import javax.sound.midi.Soundbank;
import java.util.*;

public class MyArrayList<T> implements List<T> {
    public static void main(String[] args) {
        MyArrayList<String> myArrayList = new MyArrayList<String>(10);
        String array[] = new String[12];
        for (int i = 0; i < array.length; i++) {
            array[i] = "e " + i;
        }
        Collections.addAll(myArrayList, array);
        for (int i = 0; i < myArrayList.size(); i++) {
            System.out.printf("%s \n", myArrayList.get(i));
        }
        MyArrayList<String> myArrayList1 = new MyArrayList<String>(myArrayList.size());

        Collections.copy(myArrayList1, myArrayList);

        for (int i = 0; i < myArrayList1.size(); i++) {
            System.out.printf("%s \n", myArrayList1.get(i));
        }


        Collections.sort(myArrayList1, Comparator.<String>naturalOrder());
        for (int i = 0; i < myArrayList1.size(); i++) {
            System.out.printf("%s \n", myArrayList1.get(i));
        }

        MyArrayList<Integer> myArrayList2 = new MyArrayList<Integer>(10);
        Integer arrayInt[] = new Integer[12];
        for (int i = 0; i < arrayInt.length; i++) {
            arrayInt[i] = i;
        }
        Collections.addAll(myArrayList2, arrayInt);

        Collections.sort(myArrayList2, Comparator.<Integer>reverseOrder());
        MyListInterator<Integer> ai= myArrayList2.getMyListIterator();
        for (Object e: myArrayList2){
            System.out.println(e);
        }
    }
    private final int DEFAULT_SIZE = 1;
    private int lastIndex = 0;
    private MyListInterator<T> myListIterator = null;

    transient Object[] elementData;

    public MyListInterator<T> getMyListIterator() {
        return myListIterator;
    }

    public MyArrayList(int size) {
        this.elementData = new Object[size];
    }

    public MyArrayList() {
        this.elementData = new Object[DEFAULT_SIZE];
    }

    public int size() {
        return elementData.length;
    }

    public boolean isEmpty() {

        throw new RuntimeException();
//        return false;
    }

    public boolean contains(Object o) {
        throw new RuntimeException();
//        return false;
    }

    public Iterator<T> iterator() {
        this.myListIterator = new MyListInterator<T>(this);
            return this.myListIterator;
    }

    public Object[] toArray() {
        return Arrays.copyOf(elementData, elementData.length);
    }

    public <T1> T1[] toArray(T1[] a) {
        throw new RuntimeException();
//        return null;
    }

    public boolean add(T t) {
        add(lastIndex, t);
        return true;
    }

    public boolean remove(Object o) {
        throw new RuntimeException();
//        return false;
    }

    public boolean containsAll(Collection<?> c) {
        throw new RuntimeException();
//        return false;
    }

    public boolean addAll(Collection<? extends T> c) {
        throw new RuntimeException();
//        return false;
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        throw new RuntimeException();
//        return false;
    }

    public boolean removeAll(Collection<?> c) {
        throw new RuntimeException();
//        return false;
    }

    public boolean retainAll(Collection<?> c) {
        throw new RuntimeException();
//        return false;
    }

    public void clear() {

    }

    public T get(int index) throws ArrayIndexOutOfBoundsException{
        return (T)elementData[index];
    }

    public T set(int index, T element) {
        T oldT = get(index);
        add(index, element);
        return oldT;
    }

    public void add(int index, T element) {
        if(lastIndex==elementData.length)
            elementData = Arrays.copyOf(elementData,
                    elementData.length+DEFAULT_SIZE);
        elementData[lastIndex] = element;
        lastIndex++;
    }

    public T remove(int index) {
        throw new RuntimeException();
//        return null;
    }

    public int indexOf(Object o) {
        return 0;
    }

    public int lastIndexOf(Object o) {
        return 0;
    }

    public ListIterator<T> listIterator() {
//        if(this.myListIterator ==null)
            this.myListIterator = new MyListInterator<T>(this);
        return this.myListIterator;
    }

    public ListIterator<T> listIterator(int index) {
        throw new RuntimeException();
//        return null;
    }

    public List<T> subList(int fromIndex, int toIndex) {
        throw new RuntimeException();
//        return null;
    }

}
