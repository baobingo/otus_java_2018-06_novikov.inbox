package ru.otus.l061.cache;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;


class CacheEngineGenericsImplTest {

    private static Logger logger = LoggerFactory.getLogger(CacheEngineGenericsImplTest.class);
    CacheEngineGenerics<Integer, ImageIcon> cache;

    @Test
    void lifeCacheExample() throws InterruptedException, MalformedURLException {
        int size = 5;

        cache = new CacheEngineGenericsImpl<>(size,1000,0,false);
        logger.info("1");
        for (int i = 0; i < size; i++) {
            cache.put(i, new ImageIcon(new URL("https://java.com/ga/images/jv0h.jpg")));
        }

        for (int i = 0; i < size; i++) {
            ImageIcon element = cache.get(i);
        }

        assertEquals(5, cache.getHitCount());
        assertEquals(0, cache.getMissCount());

        Thread.sleep(1100);

        for (int i = 0; i < size; i++) {
            ImageIcon element = cache.get(i);
        }

        assertEquals(5, cache.getHitCount());
        assertEquals(5, cache.getMissCount());
    }

    @Test
    void idleCacheExample() throws InterruptedException,MalformedURLException{
        int size = 10;

        cache = new CacheEngineGenericsImpl<>(size, 0, 500, false);

        for (int i = 0; i < size; i++) {
            cache.put(i, new ImageIcon(new URL("https://java.com/ga/images/jv0h.jpg")));
        }

        for (int i = 0; i < size; i++) {
            ImageIcon element = cache.get(i);
        }

        assertEquals(10, cache.getHitCount());
        assertEquals(0, cache.getMissCount());

        Thread.sleep(1000);

        for (int i = 0; i < size; i++) {
            ImageIcon element = cache.get(i);
        }

        assertEquals(10, cache.getHitCount());
        assertEquals(10, cache.getMissCount());
    }

    @Test
    void idleCacheExample1() throws InterruptedException, MalformedURLException{
        int size = 10;

        cache = new CacheEngineGenericsImpl<>(size, 0, 500, false);

        for (int i = 0; i < size; i++) {
            cache.put(i, new ImageIcon(new URL("https://java.com/ga/images/jv0h.jpg")));
        }

        for (int i = 0; i < size; i++) {
            ImageIcon element = cache.get(i);
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
            ImageIcon element = cache.get(i);
        }

        assertEquals(12, cache.getMissCount());
    }

    @Test
    void idleCacheExample2() throws InterruptedException, MalformedURLException{
        int size = 10;

         CacheEngineGenerics<Integer, MyElement<Integer,String>> cache = new CacheEngineGenericsImpl<>(size, 0, 500, false);

        for (int i = 0; i < size; i++) {
            cache.put(i, new MyElement<>(i, "String: "+0));
        }

        for (int i = 0; i < size; i++) {
            MyElement<Integer,String> element = cache.get(i);
        }

        Thread.sleep(600);


        for (int i = 0; i < size; i++) {
            MyElement<Integer,String> element = cache.get(i);
        }

        assertEquals(10, cache.getHitCount());
        assertEquals(10, cache.getMissCount());

    }
}