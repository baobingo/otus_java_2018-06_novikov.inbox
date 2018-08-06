package ru.otus.l051;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.Notification;
import javax.management.NotificationFilter;

public class MyNotificationFilter implements NotificationFilter {
    @Override
    public boolean isNotificationEnabled(Notification notification) {
        return notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION);
    }
}
