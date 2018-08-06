package ru.otus.l051;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.Timer;

//My VM Options:
//        -agentlib:jdwp=transport=dt_socket,address=14000,server=y,suspend=n
//        -Xms512m
//        -Xmx512m


/**
 * Created by tully.
 * <p>
 * Java 9 changes in logs:
 * https://dzone.com/articles/disruptive-changes-to-gc-logging-in-java-9
 */
/*
 -agentlib:jdwp=transport=dt_socket,address=14000,server=y,suspend=n
 -Xms512m
 -Xmx512m
 -XX:MaxMetaspaceSize=256m
 -verbose:gc
 -Xlog:gc*:file=./logs/gc_pid_%p.log
 -Dcom.sun.management.jmxremote.port=15000
 -Dcom.sun.management.jmxremote.authenticate=false
 -Dcom.sun.management.jmxremote.ssl=false
 -XX:+HeapDumpOnOutOfMemoryError
 -XX:HeapDumpPath=./dumps/

 jps -- list vms or ps -e | grep java
 jstack <pid> >> threaddumps.log -- get dump from pid
 jinfo -- list VM parameters
 jhat /  jvisualvm-- analyze heap dump
 */

public class Main {
    public static void main(String... args) throws Exception {
        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());
        long startTime = System.currentTimeMillis();
        int size = 15 * 1000 * 1000;
        //int size = 50 * 1000 * 1000;//for OOM with -Xms512m
        //int size = 50 * 1000 * 100; //for small dump

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ManagementFactory.getGarbageCollectorMXBeans();
        ObjectName name = new ObjectName("ru.otus:type=Benchmark");
        Benchmark mbean = new Benchmark();
        MyBeanNotificationListener myBeanNotificationListener = new MyBeanNotificationListener();
        mbs.registerMBean(mbean, name);
        subscribeToGCEvents(myBeanNotificationListener, new MyNotificationFilter());
        Timer timer = new Timer();
        timer.schedule(new MyTimerTask(myBeanNotificationListener), 0, 60000);

        mbean.setSize(size);

        try {
            mbean.run();
        }catch (OutOfMemoryError e) {
            myBeanNotificationListener.getGcCountSummary().forEach((k, v) -> System.out.println("Summary count of: " + k + " count: " + v));
            myBeanNotificationListener.getGcTimeSummary().forEach((k, v) -> System.out.println("Summary time spent of: " + k + " time: " + v/1000 + " seconds"));
            timer.cancel();
        }

    }
    private static void subscribeToGCEvents(NotificationListener notificationListener, NotificationFilter notificationFilter) {
        java.lang.management.ManagementFactory.getGarbageCollectorMXBeans().forEach(b->{
            System.out.println(b.getName());
            NotificationEmitter emitter = (NotificationEmitter) b;
            emitter.addNotificationListener(notificationListener, notificationFilter, null);
        });
    }
}
