package ru.otus.l21;

class ObjectFactory {

    enum primitiveTypes {
        BYTE,
        INT,
        BOOLEAN,
        LONG;
    }

    Object[] createObjectArray(Object object) throws Exception{
        Object[] array = new Object[10_000_000];
        for (int i = 0; i < array.length; i++) {
            array[i] = Class.forName(object.getClass().getName()).getConstructor().newInstance();
        }
        return array;
    }
    Object[] createObjectArray() throws InterruptedException{
        Object[] array = new Object[10_000_000];
        for (int i = 0; i < array.length; i++) {
            array[i] = new Object();
        }
        return array;
    }
    Object[] createObjectArrayByte() throws InterruptedException{
        Object[] array = new Object[10_000_000];
        for (int i = 0; i < array.length; i++) {
            array[i] = new byte[1];
        }
        return array;
    }
    Object[] createObjectArrayString() throws InterruptedException{
        Object[] array = new Object[10_000_000];
        for (int i = 0; i < array.length; i++) {
            array[i] = new String();
        }
        return array;
    }
    Object[] createObjectArrayStringByte() throws InterruptedException{
        Object[] array = new Object[10_000_000];
        for (int i = 0; i < array.length; i++) {
            array[i] = new String(new byte[1]);
        }
        return array;
    }
    Object[] createSomeClass(primitiveTypes type)throws InterruptedException{

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