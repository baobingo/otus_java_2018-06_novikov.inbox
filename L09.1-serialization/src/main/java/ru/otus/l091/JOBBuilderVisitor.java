package ru.otus.l091;

import javax.json.*;
import java.lang.reflect.Field;
import java.util.List;

public class JOBBuilderVisitor implements JOBBuilder {

    private VisitorInt visitorInt;
    private VisitorDouble visitorDouble;
    private VisitorString visitorString;
    private VisitorList visitorList;
    private VisitorStringArray visitorStringArray;
    private VisitorIntArray visitorIntArray;
    private VisitorDoubleArray visitorDoubleArray;

    public JOBBuilderVisitor(VisitorInt visitorInt, VisitorDouble visitorDouble, VisitorString visitorString, VisitorList visitorList, VisitorStringArray visitorStringArray, VisitorIntArray visitorIntArray, VisitorDoubleArray visitorDoubleArray) {
        this.visitorInt = visitorInt;
        this.visitorDouble = visitorDouble;
        this.visitorString = visitorString;
        this.visitorList = visitorList;
        this.visitorStringArray = visitorStringArray;
        this.visitorIntArray = visitorIntArray;
        this.visitorDoubleArray = visitorDoubleArray;
    }

    public JsonObjectBuilder JOBBuilder(Object object, JsonObjectBuilder jsonObjectBuilder){
        for (Field field : object.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object value = field.get(object);
                if (value != null) {
                    if(field.getType() == int.class) {
                        visitorInt.visit((int)value, field.getName(), jsonObjectBuilder);
                    }
                    if(field.getType() == double.class) {
                        visitorDouble.visit((double)value, field.getName(), jsonObjectBuilder);
                    }
                    if(field.getType() == String.class) {
                        visitorString.visit((String) value, field.getName(), jsonObjectBuilder);
                    }
                    if(field.getType() == List.class) {
                        visitorList.visit((List<?>)value, field.getName(), jsonObjectBuilder, this);
                    }
                    if(field.getType() == int[].class) {
                        visitorIntArray.visit((int[])value, field.getName(), jsonObjectBuilder);
                    }
                    if(field.getType() == double[].class) {
                        visitorDoubleArray.visit((double[])value, field.getName(), jsonObjectBuilder);
                    }
                    if(field.getType() == String[].class) {
                        visitorStringArray.visit((String[])value, field.getName(), jsonObjectBuilder);
                    }
                }
            }catch (IllegalAccessException e){
                e.printStackTrace();
            }
        }
        return jsonObjectBuilder;
    }
}
