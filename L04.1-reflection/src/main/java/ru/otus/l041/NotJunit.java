package ru.otus.l041;

import java.util.Arrays;

public class NotJunit {
    static Object[] beforeMethods;
    static Object[] testMethods;
    static Object[] afterMethods;

    static <T> void run(Class<T> type){
        beforeMethods = ReflectionHelper.fillArrayMethodsByAnnotation(type, "Before");
        testMethods = ReflectionHelper.fillArrayMethodsByAnnotation(type, "Test");
        afterMethods = ReflectionHelper.fillArrayMethodsByAnnotation(type, "After");

        Arrays.stream(testMethods).forEach(m-> {
            T testClass = ReflectionHelper.instantiate(type);
            Arrays.stream(beforeMethods).forEach(b -> ReflectionHelper.callMethod(testClass, b.toString()));
            ReflectionHelper.callMethod(testClass, m.toString());
            Arrays.stream(afterMethods).forEach(a -> ReflectionHelper.callMethod(testClass, a.toString()));
        });
    }
}
