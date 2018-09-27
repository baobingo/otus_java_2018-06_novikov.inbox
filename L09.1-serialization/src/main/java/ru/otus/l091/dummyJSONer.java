package ru.otus.l091;

import javax.json.*;

public class dummyJSONer {

    private static JOBBuilderVisitor JOBBuilderVisitor = new JOBBuilderVisitor();

    public static void main(String[] args) {
        System.out.println(toJson(new SampleObject()));
    }

    public static JsonObject toJson(Object object){
        return JOBBuilderVisitor.JOBBuilder(object, Json.createObjectBuilder()).build();
    }
}
