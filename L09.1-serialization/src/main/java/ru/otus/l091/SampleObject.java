package ru.otus.l091;


import java.util.List;

public class SampleObject {
    private final int valueOfInt;
    private final double valueOfDouble;
    private final String valueOfString;
    private final List<String> collectionOfString;
    private final List<Integer> collectionOfInteger;
    private final List<Double> collectionOfDouble;
    private final int[] arrayOfInt = {1, 2};
    private final String[] arrayOfString = {"100", "200"};
    private final Double[] arrayOfDouble = {15.3, 16.7};
    private final List<SampleSubObject> listOfSampleSubObject;

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
