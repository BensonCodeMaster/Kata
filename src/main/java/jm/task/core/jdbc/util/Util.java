package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    // реализуйте настройку соеденения с БД

    private static final String URL = "jdbc:mysql://localhost:3306/mysql";
    private static final String USER = "root";
    private static final String PASSWORD = "Den4ik13021999";

    public static Connection getConnection() throws SQLException {
       return DriverManager.getConnection(URL,USER,PASSWORD);
    }
}
