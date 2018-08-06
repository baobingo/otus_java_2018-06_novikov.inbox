package ru.otus.l051;

import java.util.TimerTask;

public class MyTimerTask extends TimerTask {
    MyBeanNotificationListener myBeanNotificationListener;

    public MyTimerTask(MyBeanNotificationListener myBeanNotificationListener) {
        this.myBeanNotificationListener = myBeanNotificationListener;
    }

    @Override
    public void run() {
        myBeanNotificationListener.getGcCount().forEach((k,v)->System.out.println("Minute stat of " + k + " count: " + v));
        myBeanNotificationListener.getGcTime().forEach((k,v)->System.out.println("Minute stat of " + k + " time: " + v/1000 + " seconds"));
        myBeanNotificationListener.clearGcCount();
        myBeanNotificationListener.clearGcTime();
    }
}
