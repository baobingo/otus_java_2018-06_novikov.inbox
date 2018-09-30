package ru.otus.l091;

import javax.json.JsonObjectBuilder;
import java.util.List;

public interface VisitorList {
    void visit(List<?> node, String key, JsonObjectBuilder jsonObjectBuilder, JOBBuilder jobBuilder);
}
