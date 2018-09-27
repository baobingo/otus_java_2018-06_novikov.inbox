package ru.otus.l091;

import java.util.List;

public class SampleSubObject {
    public final int age;
    public final String name;
    public List<String> listofString;

    public SampleSubObject(int age, String name) {
        this.age = age;
        this.name = name;
        this.listofString = List.of("String 1", "String 2");
    }
}
