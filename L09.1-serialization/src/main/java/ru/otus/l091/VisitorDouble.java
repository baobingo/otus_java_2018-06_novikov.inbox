package ru.otus.l091;

import javax.json.JsonObjectBuilder;

public interface VisitorDouble {
    void visit(double node, String key, JsonObjectBuilder jsonObjectBuilder);
}
