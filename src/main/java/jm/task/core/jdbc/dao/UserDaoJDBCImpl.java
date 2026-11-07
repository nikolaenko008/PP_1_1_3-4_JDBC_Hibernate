package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    //создание таблицы
    public void createUsersTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS users (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(45) NOT NULL,
                    last_name VARCHAR(45) NOT NULL,
                    age TINYINT
                )
                """;

        try (Connection conn = Util.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при создании таблицы", e);
        }
    }

    //удаление таблицы
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";

        try (Connection conn = Util.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении таблицы", e);
        }
    }

    //сохранение пользователя
    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, last_name, age) VALUES (?, ?, ?)";

        try (Connection conn = Util.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
            System.out.printf("User с именем — %s добавлен в базу данных%n", name);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при сохранении пользователя", e);
        }
    }

    //удаление по id
    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = Util.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении пользователя по id", e);
        }
    }

    //получение всех пользователей
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, name, last_name, age FROM users";

        try (Connection conn = Util.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("last_name"));
                user.setAge(rs.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении пользователей", e);
        }
        return users;
    }

    //очистка таблицы
    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE users";

        try (Connection conn = Util.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при очистке таблицы", e);
        }
    }
}
