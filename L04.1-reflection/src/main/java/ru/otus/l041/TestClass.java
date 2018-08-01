package ru.otus.l041;

import ru.otus.l041.annotations.After;
import ru.otus.l041.annotations.Before;
import ru.otus.l041.annotations.Test;

public class TestClass {
    @Before
    void TestBefore(){
        System.out.println("Test @Before");
    }
    @Test
    void Test1(){
        System.out.println("Test Test1");
    }
    @Test
    void Test2(){
        System.out.println("Test Test2");
    }
    @Test
    void Test3(){
        System.out.println("Test Test3");
    }
    @After
    void TestAfter(){
        System.out.println("Test @After");
    }
}
