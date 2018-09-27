package ru.otus.l091;

import javax.json.*;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class JOBBuilderVisitor implements Visitor {

    public JsonObjectBuilder JOBBuilder(Object object, JsonObjectBuilder jsonObjectBuilder){
        for (Field field : object.getClass().getDeclaredFields()) {
            try {
                Object value = field.get(object);
                if (value != null) {
                    if(field.getType() == int.class) {
                        visit((int)value, field.getName(), jsonObjectBuilder);
                    }
                    if(field.getType() == double.class) {
                        visit((double)value, field.getName(), jsonObjectBuilder);
                    }
                    if(field.getType() == String.class) {
                        visit((String) value, field.getName(), jsonObjectBuilder);
                    }
                    if(field.getType() == List.class) {
                        visit((List<?>)value, field.getName(), jsonObjectBuilder);
                    }
                    if(field.getType() == int[].class) {
                        visit((int[])value, field.getName(), jsonObjectBuilder);
                    }
                    if(field.getType() == double[].class) {
                        visit((double[])value, field.getName(), jsonObjectBuilder);
                    }
                    if(field.getType() == String[].class) {
                        visit((String[])value, field.getName(), jsonObjectBuilder);
                    }
                }
            }catch (IllegalAccessException e){
                e.printStackTrace();
            }
        }
        return jsonObjectBuilder;
    }

    @Override
    public void visit(int node, String key, JsonObjectBuilder jsonObjectBuilder) {
        jsonObjectBuilder.add(key, node);
    }

    @Override
    public void visit(double node, String key, JsonObjectBuilder jsonObjectBuilder) {
        jsonObjectBuilder.add(key, node);
    }

    @Override
    public void visit(String node, String key, JsonObjectBuilder jsonObjectBuilder) {
        jsonObjectBuilder.add(key, node);
    }

    @Override
    public void visit(List<?> node, String key, JsonObjectBuilder jsonObjectBuilder) {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        switch (node.get(0).getClass().getTypeName()) {
            case "java.lang.String":
                node.forEach(x -> jsonArrayBuilder.add((String) x));
                break;
            case "java.lang.Integer":
                node.forEach(x -> jsonArrayBuilder.add((Integer) x));
                break;
            case "java.lang.Double":
                node.forEach(x -> jsonArrayBuilder.add((Double) x));
                break;
            default:
                node.forEach(x -> {
                    JsonObjectBuilder child = JOBBuilder(x, Json.createObjectBuilder());
                    jsonArrayBuilder.add(child);
                });
        }
        jsonObjectBuilder.add(key, jsonArrayBuilder);
    }

    @Override
    public void visit(String[] node, String key, JsonObjectBuilder jsonObjectBuilder) {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        Arrays.stream(node).forEach(x->jsonArrayBuilder.add((String)x));
        jsonObjectBuilder.add(key, jsonArrayBuilder);
    }

    @Override
    public void visit(int[] node, String key, JsonObjectBuilder jsonObjectBuilder) {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        Arrays.stream(node).forEach(x->jsonArrayBuilder.add((int)x));
        jsonObjectBuilder.add(key, jsonArrayBuilder);
    }

    @Override
    public void visit(double[] node, String key, JsonObjectBuilder jsonObjectBuilder) {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        Arrays.stream(node).forEach(x->jsonArrayBuilder.add((double)x));
        jsonObjectBuilder.add(key, jsonArrayBuilder);
    }
}
