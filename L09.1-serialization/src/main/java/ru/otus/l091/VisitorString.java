package ru.otus.l091;

import javax.json.JsonObjectBuilder;

public interface VisitorString {
    void visit(String node, String key, JsonObjectBuilder jsonObjectBuilder);
}
