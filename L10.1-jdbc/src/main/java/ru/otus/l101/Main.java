package ru.otus.l101;

/**
 * mysql> CREATE USER 'tully'@'localhost' IDENTIFIED BY 'tully';
 * mysql> GRANT ALL PRIVILEGES ON * . * TO 'tully'@'localhost';
 * mysql> select user, host from mysql.user;
 * mysql> create database db_example;
 * mysql> SET GLOBAL time_zone = '+3:00';
 */

public class Main {
    public static void main(String[] args) {
        try (DBService dbService = new ConcreteDBService()) {
            System.out.println(dbService.getMetaData());

            dbService.createTable(UserDataSet.class);

            dbService.insertUsers(
                    new UserDataSet("Andrew", Short.parseShort("21")),
                    new UserDataSet("Mariya", Short.parseShort("18")),
                    new UserDataSet("Vova", Short.parseShort("15")),
                    new UserDataSet("Lena", Short.parseShort("17")),
                    new UserDataSet("Misha", Short.parseShort("18"))
            );

            dbService.getAllUsers(UserDataSet.class).stream().forEach(System.out::println);

            System.out.println(dbService.getUserNameById(UserDataSet.class,5));
            System.out.println(dbService.getUserNameById(UserDataSet.class,3));

            System.out.println(dbService.getUserByInstance(new UserDataSet("Mariya", Short.parseShort("18"))));

            dbService.dropTable(UserDataSet.class);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
