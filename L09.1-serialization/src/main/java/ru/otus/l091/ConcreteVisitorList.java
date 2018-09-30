package ru.otus.l091;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.util.List;

public class ConcreteVisitorList implements VisitorList {
    @Override
    public void visit(List<?> node, String key, JsonObjectBuilder jsonObjectBuilder, JOBBuilder jobBuilder) {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        switch (node.get(0).getClass().getTypeName()) {
            case "java.lang.String":
                node.forEach(x -> jsonArrayBuilder.add((String) x));
                break;
            case "java.lang.Integer":
                node.forEach(x -> jsonArrayBuilder.add((Integer) x));
                break;
            case "java.lang.Double":
                node.forEach(x -> jsonArrayBuilder.add((Double) x));
                break;
            default:
                node.forEach(x -> {
                    JsonObjectBuilder child = jobBuilder.JOBBuilder(x, Json.createObjectBuilder());
                    jsonArrayBuilder.add(child);
                });
        }
        jsonObjectBuilder.add(key, jsonArrayBuilder);
    }
}
