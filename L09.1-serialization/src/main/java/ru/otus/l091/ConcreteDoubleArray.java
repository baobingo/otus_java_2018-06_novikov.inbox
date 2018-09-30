package ru.otus.l091;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.util.Arrays;

public class ConcreteDoubleArray implements VisitorDoubleArray {
    @Override
    public void visit(double[] node, String key, JsonObjectBuilder jsonObjectBuilder) {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        Arrays.stream(node).forEach(x->jsonArrayBuilder.add((double)x));
        jsonObjectBuilder.add(key, jsonArrayBuilder);
    }
}
