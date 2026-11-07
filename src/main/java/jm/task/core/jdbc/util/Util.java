package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соединения с БД
    // Параметры подключения
    private static final String URL = "jdbc:mysql://localhost:3306/test_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";     // логин
    private static final String PASSWORD = "root"; // пароль

    // регистрируем драйвер
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Драйвер MySQL не найден. Проверьте зависимость в pom.xml / build.gradle", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка подключения к БД", e);
        }
    }
}
