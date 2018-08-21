package ru.otus.l061.cache;

/**
 * Created by tully.
 */
public interface CacheEngineGenerics<K, V> {

    void put(K key, V value);

    V get(K key);

    int getHitCount();

    int getMissCount();

    void dispose();

    long getCreationTime(K key);

    long getLastAccessTime(K key);

    void setAccessed(K key);

    void setCreationTime(K key);
}
