package ru.otus.l101;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
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
            tExecutor.execQuery(concreteORMService.getInsertQueryByObject(userDataSet), statement -> {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    try {
                        BeanUtils.setProperty(userDataSet, "id", resultSet.getInt(1));
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
                return "";
            });
        }
    }

    @Override
    public List<DataSet> getAllUsers(java.lang.Class clazz) throws SQLException {
        List<DataSet> userDataSet = new ArrayList<>();
        TExecutor tExecutor = new ConcreteTExecutor(getConnection());
        return tExecutor.execQuery("select * from `" + clazz.getSimpleName().toLowerCase() + "`", result->{
            ResultSet resultSet = result.getResultSet();
            while(resultSet.next()) {
                userDataSet.add(concreteORMService.createObjectFromResultSet(clazz, resultSet));
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
            return statement;
        }, x -> { ResultSet resultSet=x.getResultSet();  resultSet.next();
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
}
