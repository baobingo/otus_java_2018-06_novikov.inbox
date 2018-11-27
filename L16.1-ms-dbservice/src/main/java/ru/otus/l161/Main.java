package ru.otus.l161;

import ru.otus.l161.MessageSystem.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * mysql> CREATE USER 'tully'@'localhost' IDENTIFIED BY 'tully';
 * mysql> GRANT ALL PRIVILEGES ON * . * TO 'tully'@'localhost';
 * mysql> select user, host from mysql.user;
 * mysql> create database db_example;
 * mysql> SET GLOBAL time_zone = '+3:00';
 */


public class Main {
    private static final int THREADS_NUMBER = 2;
    private static ExecutorService executor = Executors.newFixedThreadPool(THREADS_NUMBER);

    public static void main(String[] args) throws InterruptedException{
        HibernateDBService hibernateDBService = new HibernateDBService();
        MessageContainer messageContainer = new LBQMessageContainer();

        while(true){
            Thread.sleep(10000);
            executor.execute(new SocketClient(messageContainer));
            executor.execute(new MessageHandler(messageContainer, hibernateDBService));
        }

    }
}
