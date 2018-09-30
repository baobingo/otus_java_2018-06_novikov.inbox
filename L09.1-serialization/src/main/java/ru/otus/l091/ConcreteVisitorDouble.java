package ru.otus.l091;

import javax.json.JsonObjectBuilder;

public class ConcreteVisitorDouble implements VisitorDouble {
    @Override
    public void visit(double node, String key, JsonObjectBuilder jsonObjectBuilder) {
        jsonObjectBuilder.add(key, node);
    }
}
