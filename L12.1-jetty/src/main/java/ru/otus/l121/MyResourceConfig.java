package ru.otus.l121;

import org.glassfish.jersey.server.ResourceConfig;

public class MyResourceConfig extends ResourceConfig {
    public MyResourceConfig() {
        register(new MyApplicationBinder());
        packages(true, "ru.otus.l121");
    }
}
