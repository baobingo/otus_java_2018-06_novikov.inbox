package ru.otus.l111;

import org.junit.jupiter.api.Test;
import ru.otus.l111.dataSets.AddressDataSet;
import ru.otus.l111.dataSets.PhoneDataSet;
import ru.otus.l111.dataSets.UserDataSet;

import javax.persistence.NoResultException;

import java.lang.reflect.Executable;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * mysql> CREATE USER 'tully'@'localhost' IDENTIFIED BY 'tully';
 * mysql> GRANT ALL PRIVILEGES ON * . * TO 'tully'@'localhost';
 * mysql> select user, host from mysql.user;
 * mysql> create database db_example;
 * mysql> SET GLOBAL time_zone = '+3:00';
 */
class HibernateDBServiceTest {

    HibernateDBService hibernateDBService = new HibernateDBService();

    @Test
    void insertUsers() {
        hibernateDBService.insertUsers(new UserDataSet("Andrew",21, new PhoneDataSet("123"),
                new AddressDataSet("Kremlin")));
        hibernateDBService.insertUsers(new UserDataSet("Andrew 1",22, new PhoneDataSet("124"),
                new AddressDataSet("Kremlin 1")));
        assertEquals(hibernateDBService.getAllUsers().size(), 2);
        hibernateDBService.shutdown();
    }

    @Test
    void getAllUsers() {
        hibernateDBService.insertUsers(new UserDataSet("Andrew",21, new PhoneDataSet("123"),
                new AddressDataSet("Kremlin")));
        hibernateDBService.insertUsers(new UserDataSet("Andrew 1",22, new PhoneDataSet("124"),
                new AddressDataSet("Kremlin 1")));
        hibernateDBService.insertUsers(new UserDataSet("Andrew 2",23, new PhoneDataSet("125"),
                new AddressDataSet("Kremlin 2")));
        hibernateDBService.insertUsers(new UserDataSet("Andrew 3",24, new PhoneDataSet("126"),
                new AddressDataSet("Kremlin 3")));
        assertEquals(hibernateDBService.getAllUsers().size(), 4);
        hibernateDBService.shutdown();
    }

    @Test
    void getUserNameById() {
        hibernateDBService.insertUsers(new UserDataSet("Andrew",21, new PhoneDataSet("123"),
                new AddressDataSet("Kremlin")));
        hibernateDBService.insertUsers(new UserDataSet("Andrew 1",22, new PhoneDataSet("124"),
                new AddressDataSet("Kremlin 1")));
        assertEquals(hibernateDBService.getUserNameById(2),"Andrew 1" );
        hibernateDBService.shutdown();
    }

    @Test
    void getUserByInstance() {
        hibernateDBService.insertUsers(new UserDataSet("Andrew",21, new PhoneDataSet("123"),
                new AddressDataSet("Kremlin")));
        hibernateDBService.insertUsers(new UserDataSet("Andrew 1",22, new PhoneDataSet("124"),
                new AddressDataSet("Kremlin 1")));
        hibernateDBService.insertUsers(new UserDataSet("Andrew 2",23, new PhoneDataSet("125"),
                new AddressDataSet("Kremlin 2")));
        hibernateDBService.insertUsers(new UserDataSet("Andrew 3",24, new PhoneDataSet("126"),
                new AddressDataSet("Kremlin 3")));
        UserDataSet userDataSet = hibernateDBService.getUserByInstance(new UserDataSet("Andrew 1",22, new PhoneDataSet("124"),
                new AddressDataSet("Kremlin 1")));
        assertEquals(userDataSet.getName(), "Andrew 1");
        assertEquals(userDataSet.getAge(), 22);
        assertEquals(userDataSet.getPhone().getNumber(),"124");
        assertEquals(userDataSet.getAddress().getStreet(), "Kremlin 1");


        assertThrows(NoResultException.class,()->{ hibernateDBService.getUserByInstance(new UserDataSet("Andrew 1",21, new PhoneDataSet("124"),
                new AddressDataSet("Kremlin 2")));});
        hibernateDBService.shutdown();

    }

    @Test
    void getUsersByAddress() {
        hibernateDBService.insertUsers(new UserDataSet("Andrew",21, new PhoneDataSet("123"),
                new AddressDataSet("Kremlin")));
        hibernateDBService.insertUsers(new UserDataSet("Andrew 1",22, new PhoneDataSet("124"),
                new AddressDataSet("Kremlin 1")));
        assertEquals(hibernateDBService.getUsersByAddress(new AddressDataSet("Kremlin 1")).get(0).getName(),"Andrew 1");
        hibernateDBService.shutdown();
    }
}