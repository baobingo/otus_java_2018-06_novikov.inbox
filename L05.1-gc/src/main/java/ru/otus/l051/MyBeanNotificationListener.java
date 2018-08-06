package ru.otus.l051;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.util.HashMap;
import java.util.Optional;

public class MyBeanNotificationListener implements NotificationListener {

    private HashMap<String,Integer> gcCount = new HashMap<>();
    private HashMap<String,Long> gcTime = new HashMap<>();
    private HashMap<String,Integer> gcCountSummary = new HashMap<>();
    private HashMap<String,Long> gcTimeSummary = new HashMap<>();

    @Override
    public void handleNotification(Notification notification, Object handback) {

            GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());

            gcCount.put(info.getGcName(), Optional.ofNullable(gcCount.get(info.getGcName())).map(c->c+1).orElse(0));
            gcTime.put(info.getGcName(), Optional.ofNullable(gcTime.get(info.getGcName())).map(c->c+info.getGcInfo().getDuration()).orElse(0L));

            gcCountSummary.put(info.getGcName(), Optional.ofNullable(gcCountSummary.get(info.getGcName())).map(c->c+1).orElse(0));
            gcTimeSummary.put(info.getGcName(), Optional.ofNullable(gcTimeSummary.get(info.getGcName())).map(c->c+info.getGcInfo().getDuration()).orElse(0L));


    }

    public HashMap<String, Integer> getGcCount() {
        return gcCount;
    }

    public HashMap<String, Long> getGcTime() {
        return gcTime;
    }

    public HashMap<String, Integer> getGcCountSummary() {
        return gcCountSummary;
    }

    public HashMap<String, Long> getGcTimeSummary() {
        return gcTimeSummary;
    }

    public void clearGcCount() {
        this.gcCount = new HashMap<>();
    }

    public void clearGcTime() {
        this.gcTime = new HashMap<>();
    }
}
