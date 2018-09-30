package ru.otus.l091;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.util.Arrays;

public class ConcreteIntArray implements VisitorIntArray {
    @Override
    public void visit(int[] node, String key, JsonObjectBuilder jsonObjectBuilder) {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        Arrays.stream(node).forEach(x->jsonArrayBuilder.add((int)x));
        jsonObjectBuilder.add(key, jsonArrayBuilder);
    }
}
