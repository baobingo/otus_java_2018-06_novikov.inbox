package ru.otus.l091;

import javax.json.JsonObjectBuilder;

public interface VisitorIntArray {
    void visit(int[] node, String key, JsonObjectBuilder jsonObjectBuilder);
}
