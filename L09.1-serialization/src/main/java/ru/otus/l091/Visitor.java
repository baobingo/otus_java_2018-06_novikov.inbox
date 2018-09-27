package ru.otus.l091;

import javax.json.*;
import java.util.List;

public interface Visitor {
    JsonObjectBuilder JOBBuilder(Object object, JsonObjectBuilder jsonObjectBuilder);
    void visit(int node, String key, JsonObjectBuilder jsonObjectBuilder);
    void visit(double node, String key, JsonObjectBuilder jsonObjectBuilder);
    void visit(String node, String key, JsonObjectBuilder jsonObjectBuilder);
    void visit(List<?> node, String key, JsonObjectBuilder jsonObjectBuilder);
    void visit(String[] node, String key, JsonObjectBuilder jsonObjectBuilder);
    void visit(int[] node, String key, JsonObjectBuilder jsonObjectBuilder);
    void visit(double[] node, String key, JsonObjectBuilder jsonObjectBuilder);
}
