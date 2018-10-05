package ru.otus.l101;

import java.sql.ResultSet;
import java.util.List;

public interface ORMService {
    String getTableStructureByClass(java.lang.Class object);
    String getInsertQueryByObject(Object object);
    DataSet createObjectFromResultSet(java.lang.Class clazz, ResultSet resultSet);
}
