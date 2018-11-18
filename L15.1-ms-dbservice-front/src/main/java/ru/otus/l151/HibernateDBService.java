package ru.otus.l151;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.l151.dao.UserDataSetDAO;
import ru.otus.l151.dataSets.*;


import java.util.List;

public class HibernateDBService implements DBService {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
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

}