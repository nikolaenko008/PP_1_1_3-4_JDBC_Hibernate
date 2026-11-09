package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final String TABLE = "users";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_LAST_NAME = "last_name";
    private static final String COL_AGE = "age";

    public UserDaoJDBCImpl() {
    }

    @Override
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

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS " + TABLE;

        try (Connection conn = Util.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении таблицы", e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO " + TABLE +
                " (" + COL_NAME + ", " + COL_LAST_NAME + ", " + COL_AGE + ") " +
                "VALUES (?, ?, ?)";

        try (Connection conn = Util.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, name);
                ps.setString(2, lastName);
                ps.setByte(3, age);

                ps.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException("Ошибка сохранения пользователя", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка подключения к БД", e);
        }
    }

    @Override
    public void removeUserById(long id) {
        String sql = "DELETE FROM " + TABLE + " WHERE " + COL_ID + " = ?";

        try (Connection conn = Util.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, id);
                ps.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException("Ошибка удаления пользователя по id", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка подключения к БД", e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT " + COL_ID + ", " + COL_NAME + ", "
                + COL_LAST_NAME + ", " + COL_AGE
                + " FROM " + TABLE;

        try (Connection conn = Util.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong(COL_ID));
                user.setName(rs.getString(COL_NAME));
                user.setLastName(rs.getString(COL_LAST_NAME));
                user.setAge(rs.getByte(COL_AGE));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка получения пользователей", e);
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE " + TABLE;

        try (Connection conn = Util.getConnection()) {
            conn.setAutoCommit(false);
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(sql);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException("Ошибка очистки таблицы", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка подключения к БД", e);
        }
    }
}
