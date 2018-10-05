package ru.otus.l101;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;
import java.sql.ResultSet;

public class ConcreteORMService implements ORMService {

    private StringBuilder tableStructure;

    @Override
    public String getTableStructureByClass(java.lang.Class clazz) {

        tableStructure = new StringBuilder();

        tableStructure.append("create table if not exists "+clazz.getSimpleName().toLowerCase()+" (");

        for (Field field : clazz.getSuperclass().getDeclaredFields()) {
            field.setAccessible(true);
            if(field.getType() == long.class && field.getName() == "id"){
                tableStructure.append("id bigint auto_increment, ");
            }
        }

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if(field.getType() == long.class){
                tableStructure.append(field.getName()+" bigint, ");
            }
            if(field.getType() == String.class){
                tableStructure.append(field.getName()+" varchar(256), ");
            }
            if(field.getType() == short.class){
                tableStructure.append(field.getName()+" tinyint(1), ");
            }
        }

        tableStructure.append("primary key (id))");

        return tableStructure.toString();
    }

    @Override
    public String getInsertQueryByObject(Object object) {

            StringBuilder tableColumn = new StringBuilder();
            StringBuilder values =  new StringBuilder();

            for (Field field : object.getClass().getSuperclass().getDeclaredFields()) {
                field.setAccessible(true);
                if(field.getName() != "id") {
                    tableColumn.append("`" + field.getName() + "`, ");
                    try {
                        values.append("'" + field.get(object) + "', ");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                tableColumn.append("`"+field.getName()+"`, ");
                try {
                    values.append("'"+field.get(object)+"', ");
                }catch (IllegalAccessException e){
                    e.printStackTrace();
                }
            };

            return "INSERT INTO `" + object.getClass().getSimpleName().toLowerCase() + "` ("+ tableColumn.replace(tableColumn.length()-2,tableColumn.length(),"").toString() + ") VALUES (" +values.replace(values.length()-2,values.length(),"").toString() + ")";

    }

    @Override
    public DataSet createObjectFromResultSet(Class clazz, ResultSet resultSet) {
        try {
            Object newInstance = clazz.getConstructor().newInstance();

            for (Field field : newInstance.getClass().getSuperclass().getDeclaredFields()) {
                field.setAccessible(true);
                BeanUtils.setProperty(newInstance, field.getName(), resultSet.getString(field.getName()));
            }

            for (Field field : newInstance.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                BeanUtils.setProperty(newInstance, field.getName(), resultSet.getString(field.getName()));
            };

           return (DataSet)newInstance;

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
