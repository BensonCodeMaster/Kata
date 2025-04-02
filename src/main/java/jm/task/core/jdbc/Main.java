package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм
        UserService userService = new UserServiceImpl(new UserDaoHibernateImpl());

        userService.createUsersTable();
        userService.saveUser("Ivan","Ivanov",(byte) 20);
        userService.saveUser("Misha","Petrov",(byte) 20);
        userService.saveUser("Oleg","Sidorov",(byte) 20);
        userService.saveUser("Vladislav","Horstmann",(byte) 20);

        for (User u : userService.getAllUsers()) {
            System.out.println(u);
        }

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
