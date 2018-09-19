package ru.otus.l061.cache;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;


class CacheEngineImplTest {

    private static Logger logger = LoggerFactory.getLogger(CacheEngineImplTest.class);
    CacheEngine<Integer, String> cache;

    //VM options: -Xmx256m -Xms256m
    @Test
    void OOMTest(){
        int size = 2_400_000;

        //call method will OOM Exception as result
        //cache = new CacheEngineImplStrongRef<>(size, 1000, 0, false);
        cache = new CacheEngineImpl<>(size, 1000, 0, false);

        for (int i = 0; i < size; i++) {
            cache.put(new MyElement<>(i, "String: " + i));
        }

        assertEquals(true,true);
    }

    @Test
    void lifeCacheExample() throws InterruptedException{
        int size = 5;
        logger.info("1");
        cache = new CacheEngineImpl<>(size, 1000, 0, false);

        for (int i = 0; i < size; i++) {
            cache.put(new MyElement<>(i, "String: " + i));
        }

        for (int i = 0; i < size; i++) {
            MyElement<Integer, String> element = cache.get(i);
        }

        assertEquals(5, cache.getHitCount());
        assertEquals(0, cache.getMissCount());

        Thread.sleep(1100);

        for (int i = 0; i < size; i++) {
            MyElement<Integer, String> element = cache.get(i);
        }

        assertEquals(5, cache.getHitCount());
        assertEquals(5, cache.getMissCount());
    }

    @Test
    void idleCacheExample() throws InterruptedException{
        int size = 10;

        cache = new CacheEngineImpl<>(size, 0, 500, false);

        for (int i = 0; i < size; i++) {
            cache.put(new MyElement<>(i, "String: " + i));
        }

        for (int i = 0; i < size; i++) {
            MyElement<Integer, String> element = cache.get(i);
        }

        assertEquals(10, cache.getHitCount());
        assertEquals(0, cache.getMissCount());

        Thread.sleep(1000);

        for (int i = 0; i < size; i++) {
            MyElement<Integer, String> element = cache.get(i);
        }

        assertEquals(10, cache.getHitCount());
        assertEquals(10, cache.getMissCount());
    }

    @Test
    void idleCacheExample1() throws InterruptedException{
        int size = 10;

        cache = new CacheEngineImpl<>(size, 0, 500, false);

        for (int i = 0; i < size; i++) {
            cache.put(new MyElement<>(i, "String: " + i));
        }

        for (int i = 0; i < size; i++) {
            MyElement<Integer, String> element = cache.get(i);
        }

        assertEquals(10, cache.getHitCount());
        assertEquals(0, cache.getMissCount());
        Thread.sleep(200);

        cache.get(0);

        Thread.sleep(400);

        cache.get(0);
        cache.get(2);
        cache.get(4);

        assertEquals(12, cache.getHitCount());
        assertEquals(2, cache.getMissCount());

        Thread.sleep(1000);

        for (int i = 0; i < size; i++) {
            MyElement<Integer, String> element = cache.get(i);
        }

        assertEquals(12, cache.getMissCount());
    }

    @Test
    void idleCacheExample2() throws InterruptedException{
        int size = 10;

        cache = new CacheEngineImpl<>(size, 0, 500, false);

        for (int i = 0; i < size; i++) {
            cache.put(new MyElement<>(i, "String: " + i));
        }

        for (int i = 0; i < size; i++) {
            MyElement<Integer, String> element = cache.get(i);
        }

        Thread.sleep(600);


        for (int i = 0; i < size; i++) {
            MyElement<Integer, String> element = cache.get(i);
        }

        assertEquals(10, cache.getHitCount());
        assertEquals(10, cache.getMissCount());

    }
}