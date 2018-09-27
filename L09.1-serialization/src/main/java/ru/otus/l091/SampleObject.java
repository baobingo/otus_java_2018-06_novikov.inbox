package ru.otus.l091;


import java.util.List;

public class SampleObject {
    public final int valueOfInt;
    public final double valueOfDouble;
    public final String valueOfString;
    public final List<String> collectionOfString;
    public final List<Integer> collectionOfInteger;
    public final List<Double> collectionOfDouble;
    public final int[] arrayOfInt = {1, 2};
    public final String[] arrayOfString = {"100", "200"};
    public final Double[] arrayOfDouble = {15.3, 16.7};
    public final List<SampleSubObject> listOfSampleSubObject;


    public SampleObject() {
        valueOfInt = 1;
        valueOfDouble = 2;
        valueOfString = "Some string";
        collectionOfString = List.of("String 1", "String 2");
        collectionOfInteger = List.of(5, 6);
        collectionOfDouble = List.of(21.0, 34.50);
        listOfSampleSubObject = List.of(new SampleSubObject(1,"Name 1"), new SampleSubObject(2, "Name 2"));
    }
}
