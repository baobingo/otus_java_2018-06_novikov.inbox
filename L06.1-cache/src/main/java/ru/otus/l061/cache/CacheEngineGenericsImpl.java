package ru.otus.l061.cache;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;

public class CacheEngineGenericsImpl<K, V> implements CacheEngineGenerics<K, V> {

    private static final int TIME_THRESHOLD_MS = 5;

    private final int maxElements;
    private final long lifeTimeMs;
    private final long idleTimeMs;
    private final boolean isEternal;

    private final Map<K, SoftReference<V>> elements = new LinkedHashMap<>();
    private final Map<K, Long> creationTimes= new LinkedHashMap<>();
    private final Map<K, Long> accessedTimes = new LinkedHashMap<>();

    private final Timer timer = new Timer();

    private int hit = 0;
    private int miss = 0;

    CacheEngineGenericsImpl(int maxElements, long lifeTimeMs, long idleTimeMs, boolean isEternal) {
        this.maxElements = maxElements;
        this.lifeTimeMs = lifeTimeMs > 0 ? lifeTimeMs : 0;
        this.idleTimeMs = idleTimeMs > 0 ? idleTimeMs : 0;
        this.isEternal = lifeTimeMs == 0 && idleTimeMs == 0 || isEternal;
    }

    public void put(K key, V value) {
        if (elements.size() == maxElements) {
            K firstKey = elements.keySet().iterator().next();
            elements.remove(firstKey);
            creationTimes.remove(firstKey);
            accessedTimes.remove(firstKey);
        }

        elements.put(key, new SoftReference<>(value));
        setCreationTime(key);
        setAccessed(key);

        if (!isEternal) {
            if (lifeTimeMs != 0) {
                TimerTask lifeTimerTask = getTimerTask(key, lifeElement -> getCreationTime(key) + lifeTimeMs);
                timer.schedule(lifeTimerTask, lifeTimeMs);
            }
            if (idleTimeMs != 0) {
                TimerTask idleTimerTask = getTimerTaskIdle(key);
                timer.schedule(idleTimerTask, idleTimeMs, idleTimeMs);
            }
        }
    }

    public V get(K key) {
        SoftReference<V> element = elements.get(key);
        if (element!= null) {
            hit++;
            setAccessed(key);
            return element.get();
        } else {
            miss++;
        }
        return null;
    }

    public int getHitCount() {
        return hit;
    }

    public int getMissCount() {
        return miss;
    }

    @Override
    public void dispose() {
        timer.cancel();
    }

    private TimerTask getTimerTask(final K key, Function<V, Long> timeFunction) {
        return new TimerTask() {
            @Override
            public void run() {
                SoftReference<V> element = elements.get(key);
                if (element == null || isT1BeforeT2(timeFunction.apply(element.get()), System.currentTimeMillis())) {
                    elements.remove(key);
                    this.cancel();
                }
            }
        };
    }

    private TimerTask getTimerTaskIdle(final K key) {
        return new TimerTask() {
            @Override
            public void run() {
                SoftReference<V> element = elements.get(key);
                if (element == null || isT1BeforeT2(idleTimeMs, System.currentTimeMillis()-getLastAccessTime(key))) {
                    elements.remove(key);
                    this.cancel();
                }
            }
        };
    }


    private boolean isT1BeforeT2(long t1, long t2) {
        return t1 < t2 + TIME_THRESHOLD_MS;
    }

    @Override
    public long getCreationTime(K key) {
        return creationTimes.get(key);
    }

    @Override
    public long getLastAccessTime(K key) {
        return accessedTimes.get(key);
    }

    @Override
    public void setAccessed(K key) {
        accessedTimes.put(key, System.currentTimeMillis());
    }

    @Override
    public void setCreationTime(K key) {
        creationTimes.put(key, System.currentTimeMillis());
    }
}
