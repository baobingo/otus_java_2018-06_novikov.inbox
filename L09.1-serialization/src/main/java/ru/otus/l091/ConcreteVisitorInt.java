package ru.otus.l091;

import javax.json.JsonObjectBuilder;

public class ConcreteVisitorInt implements VisitorInt {
    @Override
    public void visit(int node, String key, JsonObjectBuilder jsonObjectBuilder) {
        jsonObjectBuilder.add(key, node);
    }
}
