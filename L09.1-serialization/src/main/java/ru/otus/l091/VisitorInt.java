package ru.otus.l091;

import javax.json.JsonObjectBuilder;

public interface VisitorInt {
    void visit(int node, String key, JsonObjectBuilder jsonObjectBuilder);
}
