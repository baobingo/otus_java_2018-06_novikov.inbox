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
        List<String> result = concreteORMService.getInsertQueryByObject((Object[])userDataSets);
        for (String s : result) {
            TExecutor tExecutor = new ConcreteTExecutor(getConnection());
            tExecutor.execUpdate(s);
        }
    }

    @Override
    public List<UserDataSet> getAllUsers(java.lang.Class clazz) throws SQLException {
        List<UserDataSet> userDataSet = new ArrayList<>();
        TExecutor tExecutor = new ConcreteTExecutor(getConnection());
        return tExecutor.execQuery("select * from `" + clazz.getSimpleName().toLowerCase() + "`", result->{
            while(result.next()) {
                userDataSet.add(new UserDataSet(Long.parseLong(result.getString("id")), result.getString("name"), Short.parseShort(result.getString("age"))));
            }
            return userDataSet;
        });
    }

    @Override
    public String getUserNameById(java.lang.Class clazz, int id) throws SQLException {
        TExecutor tExecutor = new ConcreteTExecutor(getConnection());
        return tExecutor.execQuery("select * from `" + clazz.getSimpleName().toLowerCase() + "`", result->{
            while(result.next()){
                if(Integer.parseInt(result.getString("id"))==id){
                    return "User by ID " + result.getString("id") + " is " + result.getString("name");
                }
            }
            return "";
        });
    }

    @Override
    public UserDataSet getUserByInstance(UserDataSet userDataSet) throws SQLException {
        List<UserDataSet> allUsers = getAllUsers(userDataSet.getClass());
        return allUsers.stream().filter(x->x.getName().equals(userDataSet.getName())&&x.getAge()==userDataSet.getAge()).findFirst().get();
    }

    @Override
    public void close() throws Exception {
        connection.close();
        System.out.println("Connection closed");
    }
}
