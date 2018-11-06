package ru.otus.l121;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class MyResourceConfig extends ResourceConfig {
    public MyResourceConfig() {
        register(new MyApplicationBinder());
        register(JacksonFeature.class);
        packages(true, "ru.otus.l121");
    }
}
