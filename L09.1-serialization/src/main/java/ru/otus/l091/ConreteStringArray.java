package ru.otus.l091;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.util.Arrays;

public class ConreteStringArray implements VisitorStringArray {
    @Override
    public void visit(String[] node, String key, JsonObjectBuilder jsonObjectBuilder) {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        Arrays.stream(node).forEach(x->jsonArrayBuilder.add((String)x));
        jsonObjectBuilder.add(key, jsonArrayBuilder);
    }
}
