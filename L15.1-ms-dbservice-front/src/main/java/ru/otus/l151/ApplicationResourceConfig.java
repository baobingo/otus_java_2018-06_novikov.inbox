package ru.otus.l151;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("rest")
public class ApplicationResourceConfig extends ResourceConfig {

    public ApplicationResourceConfig() {
        packages("ru.otus.l151");
        register(RESTService.class);
        register(JacksonFeature.class);
    }
}

