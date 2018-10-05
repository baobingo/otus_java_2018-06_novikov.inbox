package ru.otus.l101;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConcreteDBService implements DBService {

    private final Connection connection;
    private final ConcreteORMService concreteORMService;

    public Connection getConnection() {
        return connection;
    }

    public ConcreteDBService() {
        this.connection = ConnectionHelper.getConnection();
        this.concreteORMService = new ConcreteORMService();
    }

    @Override
    public void createTable(java.lang.Class userDataSet) throws SQLException {
        TExecutor tExecutor = new ConcreteTExecutor(getConnection());
        tExecutor.execUpdate(concreteORMService.getTableStructureByClass(UserDataSet.class));
    }

    @Override
    public String getMetaData() throws SQLException {
        return "Connected to: " + getConnection().getMetaData().getURL() + "\n" +
                "DB name: " + getConnection().getMetaData().getDatabaseProductName() + "\n" +
                "DB version: " + getConnection().getMetaData().getDatabaseProductVersion() + "\n" +
                "Driver: " + getConnection().getMetaData().getDriverName();
    }

    @Override
    public void dropTable(java.lang.Class clazz) throws SQLException {
        TExecutor tExecutor = new ConcreteTExecutor(getConnection());
        tExecutor.execUpdate("DROP TABLE IF EXISTS "+clazz.getSimpleName().toLowerCase());
    }

    @Override
    public void insertUsers(UserDataSet... userDataSets) throws SQLException {
        for (UserDataSet userDataSet : userDataSets) {
            TExecutor tExecutor = new ConcreteTExecutor(getConnection());
            tExecutor.execUpdate(concreteORMService.getInsertQueryByObject(userDataSet));
            userDataSet.setId(getLastAddedId(userDataSet.getClass()));
        }
    }

    @Override
    public List<DataSet> getAllUsers(java.lang.Class clazz) throws SQLException {
        List<DataSet> userDataSet = new ArrayList<>();
        TExecutor tExecutor = new ConcreteTExecutor(getConnection());
        return tExecutor.execQuery("select * from `" + clazz.getSimpleName().toLowerCase() + "`", result->{
            while(result.next()) {
                userDataSet.add(concreteORMService.createObjectFromResultSet(clazz, result));
            }
            return userDataSet;
        });
    }

    @Override
    public String getUserNameById(java.lang.Class clazz, int id) throws SQLException {
        PreparedTExecutor preparedTExecutor = new ConcretePreparedTExecutor(getConnection());

        return preparedTExecutor.execQuery("select * from `" + clazz.getSimpleName().toLowerCase() + "` WHERE id=(?)", statement -> {
            statement.setString(1, String.valueOf(id));
            statement.execute();
            return statement.getResultSet();
        }, resultSet -> { resultSet.next();
            return resultSet.getString("name");
        });
    }

    @Override
    public DataSet getUserByInstance(UserDataSet userDataSet) throws SQLException {
        List<DataSet> allUsers = getAllUsers(userDataSet.getClass());
        return allUsers.stream().map(x->(UserDataSet)x).filter(x->x.getName().equals(userDataSet.getName())&&x.getAge()==userDataSet.getAge()).findFirst().get();
    }

    @Override
    public void close() throws Exception {
        connection.close();
        System.out.println("Connection closed");
    }

    public int getLastAddedId(java.lang.Class clazz) throws SQLException{
        PreparedTExecutor preparedTExecutor = new ConcretePreparedTExecutor(getConnection());

        return preparedTExecutor.execQuery("select max(id) id from `" + clazz.getSimpleName().toLowerCase() + "` ", statement -> {
            statement.execute();
            return statement.getResultSet();
        }, resultSet -> { resultSet.next();
            return  Integer.parseInt(resultSet.getString("id"));
        });
    }
}
