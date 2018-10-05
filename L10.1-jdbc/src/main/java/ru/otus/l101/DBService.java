package ru.otus.l101;

import java.sql.SQLException;
import java.util.List;

public interface DBService extends AutoCloseable {
    void createTable(java.lang.Class clazz) throws SQLException;
    String getMetaData() throws SQLException;
    void dropTable(java.lang.Class clazz) throws SQLException;

    void insertUsers(UserDataSet... userDataSets) throws SQLException;
    List<DataSet> getAllUsers(java.lang.Class clazz) throws SQLException;
    String getUserNameById(java.lang.Class clazz, int id) throws SQLException;
    DataSet getUserByInstance(UserDataSet userDataSet) throws SQLException;
}
