package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Name0", "Lastname0", (byte) 0);
        System.out.println("User с именем Name0 добавлен в базу данных");
        userService.saveUser("Name1", "Lastname1", (byte) 10);
        System.out.println("User с именем Name1 добавлен в базу данных");
        userService.saveUser("Name2", "Lastname2", (byte) 100);
        System.out.println("User с именем Name2 добавлен в базу данных");
        userService.saveUser("Name3", "Lastname3", (byte) 33);
        System.out.println("User с именем Name3 добавлен в базу данных");

        userService.getAllUsers().forEach(System.out::println);

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
