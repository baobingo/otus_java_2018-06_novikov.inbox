package ru.otus.l21;

import java.lang.management.ManagementFactory;

@SuppressWarnings({"RedundantStringConstructorCall", "InfiniteLoopStatement"})
public class Main {
    public static void main(String... args) throws Exception {
        System.out.println("pid: " + ManagementFactory.getRuntimeMXBean().getName());

        ObjectFactory objectFactory = new ObjectFactory();
        MeasureTool.getMem();
        MeasureTool.setLastMem(MeasureTool.getMem());

        MeasureTool.makeMeasure(objectFactory.createObjectArray());
        MeasureTool.makeMeasure(objectFactory.createObjectArrayString());
        MeasureTool.makeMeasure(objectFactory.createObjectArrayStringByte());
        MeasureTool.makeMeasure(objectFactory.createObjectArrayByte());
        MeasureTool.makeMeasure(objectFactory.createSomeClass(ObjectFactory.primitiveTypes.BYTE));
        MeasureTool.makeMeasure(objectFactory.createSomeClass(ObjectFactory.primitiveTypes.INT));
        MeasureTool.makeMeasure(objectFactory.createSomeClass(ObjectFactory.primitiveTypes.BOOLEAN));
        MeasureTool.makeMeasure(objectFactory.createSomeClass(ObjectFactory.primitiveTypes.LONG));
        MeasureTool.makeMeasure(objectFactory.createObjectArray(new String()));
        MeasureTool.makeMeasure(objectFactory.createObjectArray(new SomeClassLONG()));

        MeasureTool.makeMeasure(objectFactory.createObjectArray(""));
        MeasureTool.makeMeasureAll(objectFactory.createObjectArray(10));
        MeasureTool.makeMeasureAll(objectFactory.createObjectArray(100));
        MeasureTool.makeMeasureAll(objectFactory.createObjectArray(1000));
    }
}
