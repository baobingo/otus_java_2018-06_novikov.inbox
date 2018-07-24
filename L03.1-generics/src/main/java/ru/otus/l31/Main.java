package ru.otus.l31;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        MyArrayList<String> myArrayList = new MyArrayList<String>(12);

        String array[] = new String[12];
        for (int i = 0; i < array.length; i++) {
            array[i] = "e " + i;
        }

        Collections.addAll(myArrayList, array);

        ArrayList<String> arrayList = new ArrayList<String>(myArrayList);

        MyArrayList<String> myArrayList1 = new MyArrayList<String>(myArrayList.size());
        for (int i = 0; i < array.length; i++) {
            array[i] = "d " + i;
        }
        Collections.addAll(myArrayList1, array);

        Collections.copy(myArrayList1, myArrayList);
        for (int i = 0; i < myArrayList1.size(); i++) {
            System.out.printf("%s \n", myArrayList1.get(i));
        }

        Collections.sort(myArrayList1, Comparator.<String>naturalOrder());
        for (int i = 0; i < myArrayList1.size(); i++) {
            System.out.printf("%s \n", myArrayList1.get(i));
        }

        MyArrayList<Integer> myArrayList2 = new MyArrayList<Integer>(12);
        Integer arrayInt[] = new Integer[12];
        for (int i = 0; i < arrayInt.length; i++) {
            arrayInt[i] = i;
        }

        Collections.addAll(myArrayList2, arrayInt);
        for (Object e: myArrayList2){
            System.out.println(e);
        }

        Collections.sort(myArrayList2, Comparator.<Integer>reverseOrder());
        for (Object e: myArrayList2){
            System.out.println(e);
        }
    }
}
