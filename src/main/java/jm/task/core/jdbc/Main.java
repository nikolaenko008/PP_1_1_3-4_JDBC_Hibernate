package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserDaoJDBCImpl dao = new UserDaoJDBCImpl();

        dao.createUsersTable();
        dao.saveUser("Иван", "Пупкин", (byte) 25);
        dao.saveUser("Александр", "Петров", (byte) 37);
        dao.saveUser("Сергей", "Николаенко", (byte) 35);
        dao.saveUser("Анастасия", "Михова", (byte) 32);
        dao.getAllUsers().forEach(System.out::println);
        dao.cleanUsersTable();
        dao.dropUsersTable();
    }
}
