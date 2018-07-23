package ru.otus.l21;

import java.lang.management.ManagementFactory;
import java.util.function.Supplier;

@SuppressWarnings({"RedundantStringConstructorCall", "InfiniteLoopStatement"})
public class Main {
    public static void main(String... args) throws Exception {
        System.out.println("pid: " + ManagementFactory.getRuntimeMXBean().getName());

        MeasureTool.getMem();
        MeasureTool.setLastMem(MeasureTool.getMem());
//
        MeasureTool.makeMeasure(ObjectFactory.createObjectArray());
        MeasureTool.makeMeasure(ObjectFactory.createObjectArrayString());
        MeasureTool.makeMeasure(ObjectFactory.createObjectArrayStringByte());
//        MeasureTool.makeMeasure(objectFactory.createObjectArrayByte());
//        MeasureTool.makeMeasure(objectFactory.createSomeClass(primitiveTypes.BYTE));
//        MeasureTool.makeMeasure(objectFactory.createSomeClass(primitiveTypes.INT));
//        MeasureTool.makeMeasure(objectFactory.createSomeClass(primitiveTypes.BOOLEAN));
//        MeasureTool.makeMeasure(objectFactory.createSomeClass(primitiveTypes.LONG));
//        MeasureTool.makeMeasure(objectFactory.createObjectArray(new String()));
//        MeasureTool.makeMeasure(objectFactory.createObjectArray(new SomeClassLONG()));
//
//        MeasureTool.makeMeasure(objectFactory.createObjectArray(""));
//        MeasureTool.makeMeasureAll(objectFactory.createObjectArray(10));
//        MeasureTool.makeMeasureAll(objectFactory.createObjectArray(100));
//        MeasureTool.makeMeasureAll(objectFactory.createObjectArray(1000));

        Supplier<Object[]> supplier = ObjectFactory::createObjectArray;
        MeasureTool.makeMeasure(supplier);

        supplier = ObjectFactory::createObjectArrayString;
        MeasureTool.makeMeasure(supplier);

        supplier = ObjectFactory::createObjectArrayStringByte;
        MeasureTool.makeMeasure(supplier);

        supplier = ObjectFactory::createSomeClassBYTE;
        MeasureTool.makeMeasure(supplier);



    }
}
