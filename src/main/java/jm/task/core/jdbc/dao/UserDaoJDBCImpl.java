package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    Connection connection;

    {
        try {
             connection = Util.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String createTableSQL = """
                CREATE TABLE IF NOT EXISTS users (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(50) NOT NULL,
                    lastname VARCHAR(100) NOT NULL,
                    age TINYINT NOT NULL
                ) ENGINE=InnoDB;
                """;

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableSQL);
        } catch (SQLException e) {
            System.err.println("Ошибка при создании таблицы" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate("DROP TABLE IF EXISTS users");
        } catch (SQLException exception) {
            System.err.println("Ошибка при удалении таблицы!" + exception.getMessage());
            exception.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String insertSQL = "INSERT INTO users (name, lastName, age) VALUES (?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(insertSQL)){
            statement.setString(1, name);
            statement.setString(2,lastName);
            statement.setByte(3, age);

            statement.executeUpdate();
        } catch (SQLException e ){
            System.out.println("Ошибка при добавлении пользователя: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void removeUserById(long id) {

        String insertSQL = "DELETE FROM users WHERE id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(insertSQL)){
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();
            if(rowsDeleted > 0) {
                System.out.println("Пользователь с id " + id + " успешно удален!" );
            } else {
                System.out.println("Пользователь с id " + id + " не найден.");
            }
        } catch(SQLException e){
            System.out.println("Ошибка при удалении пользователя по id: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();
        String selectSQL = "SELECT * FROM users";

        try (Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectSQL)){

            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }

        } catch (SQLException e){
            System.err.println("Ошибка при получении списка пользователей: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE users");
            System.out.println("Таблица 'users' очищена!");
        } catch (SQLException e){
            System.err.println("Ошибка при очистке таблицы: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
