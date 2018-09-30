package ru.otus.l091;

import javax.json.JsonObjectBuilder;

public class ConcreteVisitorString implements VisitorString {
    @Override
    public void visit(String node, String key, JsonObjectBuilder jsonObjectBuilder) {
        jsonObjectBuilder.add(key, node);
    }
}
