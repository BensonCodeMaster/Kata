package jm.task.core.jdbc.util;

import java.sql.*;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

public class Util {
    // реализуйте настройку соеденения с БД

    private static final String URL = "jdbc:mysql://localhost:3306/mysql";
    private static final String USER = "root";
    private static final String PASSWORD = "Den4ik13021999";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            Configuration configuration = new Configuration();

            configuration.setProperty(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            configuration.setProperty(Environment.URL, "jdbc:mysql://localhost:3306/mysql");
            configuration.setProperty(Environment.USER, "root");
            configuration.setProperty(Environment.PASS, "Den4ik13021999");
            configuration.setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
            configuration.setProperty(Environment.SHOW_SQL, "true");
            configuration.setProperty(Environment.HBM2DDL_AUTO, "update");

            configuration.addAnnotatedClass(jm.task.core.jdbc.model.User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }
}
