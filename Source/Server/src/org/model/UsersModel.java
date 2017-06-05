package org.model;

import java.util.List;
import org.gateway.UsersGateway;

public class UsersModel {

    public List<User> readUsers() {
        UsersGateway usersGateway = new UsersGateway();
        return usersGateway.read();
    }

    public void createUser(User user) {
        UsersGateway usersGateway = new UsersGateway();
        usersGateway.create(user);
    }

    public void deleteUser(User user) {
        UsersGateway usersGateway = new UsersGateway();
        usersGateway.delete(user);
    }

    public void updateUser(User oldUser, User newUser) {
        UsersGateway usersGateway = new UsersGateway();
        usersGateway.update(oldUser, newUser);
    }

}
