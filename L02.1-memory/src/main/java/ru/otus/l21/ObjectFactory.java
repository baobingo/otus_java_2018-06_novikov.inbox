package ru.otus.l21;

import java.util.function.Supplier;

enum primitiveTypes {
    BYTE,
    INT,
    BOOLEAN,
    LONG;
}

class ObjectFactory {

    static Object[] createObjectArray(Object object) throws Exception{
        Object[] array = new Object[10_000_000];
        for (int i = 0; i < array.length; i++) {
            array[i] = Class.forName(object.getClass().getName()).getConstructor().newInstance();
        }
        return array;
    }
    static Object[] createObjectArray(){
        Object[] array = new Object[10_000_000];
        for (int i = 0; i < array.length; i++) {
            array[i] = new Object();
        }
        return array;
    }

    static Object[] createObjectArray(int size){
        Object[] array = new Object[size];
        for (int i = 0; i < array.length; i++) {
            array[i] = new Object();
        }
        return array;
    }

    static Object[] createObjectArrayByte(){
        Object[] array = new Object[10_000_000];
        for (int i = 0; i < array.length; i++) {
            array[i] = new byte[1];
        }
        return array;
    }
    static Object[] createObjectArrayString(){
        Object[] array = new Object[10_000_000];
        for (int i = 0; i < array.length; i++) {
            array[i] = new String();
        }
        return array;
    }
    static Object[] createObjectArrayStringByte(){
        Object[] array = new Object[10_000_000];
        for (int i = 0; i < array.length; i++) {
            array[i] = new String(new byte[1]);
        }
        return array;
    }
    static Object[] createSomeClass(primitiveTypes type){

        if(type==primitiveTypes.BYTE){
            Object[] array = new Object[10_000_000];
            for (int i = 0; i < array.length; i++) {
                array[i] = new SomeClassBYTE();
            }
            return array;
        }
        if(type==primitiveTypes.INT){
            Object[] array = new Object[10_000_000];
            for (int i = 0; i < array.length; i++) {
                array[i] = new SomeClassINT();
            }
            return array;
        }
        if(type==primitiveTypes.BOOLEAN){
            Object[] array = new Object[10_000_000];
            for (int i = 0; i < array.length; i++) {
                array[i] = new SomeClassBOOLEAN();
            }
            return array;
        }
        if(type==primitiveTypes.LONG){
            Object[] array = new Object[10_000_000];
            for (int i = 0; i < array.length; i++) {
                array[i] = new SomeClassLONG();
            }
            return array;
        }
        return new Object[1];
    }

    public static Object[] createSomeClassBYTE() {
        Object[] array = new Object[10_000_000];
        for (int i = 0; i < array.length; i++) {
            array[i] = new SomeClassBYTE();
        }
        return array;
    }
}

class SomeClassBYTE{
    private byte b = 0;

    public SomeClassBYTE() {
    }
}
class SomeClassINT{
    private int i = 0;

    public SomeClassINT() {
    }
}
class SomeClassBOOLEAN{
    boolean b = true;

    public SomeClassBOOLEAN() {
    }
}
class SomeClassLONG{
    long l = 1L;

    public SomeClassLONG() {
    }
}