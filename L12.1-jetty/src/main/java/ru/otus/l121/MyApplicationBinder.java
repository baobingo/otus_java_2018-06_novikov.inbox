package ru.otus.l121;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class MyApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(HibernateDBService.class).to(DBService.class);
    }
}
