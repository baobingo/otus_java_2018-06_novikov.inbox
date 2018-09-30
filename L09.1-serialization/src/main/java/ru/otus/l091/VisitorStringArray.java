package ru.otus.l091;

import javax.json.JsonObjectBuilder;

public interface VisitorStringArray {
    void visit(String[] node, String key, JsonObjectBuilder jsonObjectBuilder);
}
