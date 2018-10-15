package ru.otus.l121;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.otus.l121.dataSets.*;


import javax.inject.Singleton;
import java.util.List;

@Singleton
public class HibernateDBService implements DBService {

    private final SessionFactory sessionFactory;

    public HibernateDBService() {
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(UserDataSet.class);
        configuration.addAnnotatedClass(PhoneDataSet.class);
        configuration.addAnnotatedClass(AddressDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/db_example");
        configuration.setProperty("hibernate.connection.username", "tully");
        configuration.setProperty("hibernate.connection.password", "tully");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        configuration.setProperty("hibernate.connection.useSSL", "false");
        configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");

        sessionFactory = createSessionFactory(configuration);

        startupFill();
    }

    public HibernateDBService(Configuration configuration) {
        sessionFactory = createSessionFactory(configuration);
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    public String getMetaData(){
        TExecutor tExecutor = new HibernateTExecutor(getSessionFactory());
        return tExecutor.runInSession(session -> {
            return session.getTransaction().getStatus().name();
        });
    }

    @Override
    public void insertUsers(UserDataSet... userDataSets){
        for (UserDataSet userDataSet : userDataSets) {
            TExecutor tExecutor = new HibernateTExecutor(getSessionFactory());
            tExecutor.runInSession(session -> {
                UserDataSetDAO dao = new UserDataSetDAO(session);
                dao.insertUserDataSet(userDataSet);
            return 1;
            });
        }
    }

    @Override
    public List<UserDataSet> getAllUsers() {
        TExecutor tExecutor = new HibernateTExecutor(getSessionFactory());
        return tExecutor.runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            return dao.readAll();
        });
    }

    @Override
    public String getUserNameById(int id) {
        TExecutor tExecutor = new HibernateTExecutor(getSessionFactory());
        return tExecutor.runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            return "{\"username\":\""+dao.read(id).getName()+"\"}";
        });
    }

    @Override
    public UserDataSet getUserByExample(UserDataSet userDataSet) {
        TExecutor tExecutor = new HibernateTExecutor(getSessionFactory());
        return tExecutor.runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            return dao.read(userDataSet);
        });
    }

    @Override
    public List<UserDataSet> getUsersByAddress(AddressDataSet addressDataSet) {
        TExecutor tExecutor = new HibernateTExecutor(getSessionFactory());
        return tExecutor.runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            return dao.read(addressDataSet);
        });
    }

    @Override
    public int count() {
        TExecutor tExecutor = new HibernateTExecutor(getSessionFactory());
        return tExecutor.runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            return dao.count();
        });
    }

    public void shutdown() {
        sessionFactory.close();
    }

    private void startupFill(){
        this.insertUsers(new UserDataSet("User_1",21, new PhoneDataSet("123"),
                new AddressDataSet("site")));
        this.insertUsers(new UserDataSet("User_2",22, new PhoneDataSet("124"),
                new AddressDataSet("site")));
    }
}