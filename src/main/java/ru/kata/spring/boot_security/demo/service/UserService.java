package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    List<User> index();
    void save(User user);
    User getUser(long id);
    void update(User user);
    void delete(long id);
    public User setRolesToUser(User user, long[] rolesIdArr);
    public void setEncryptedPassword(User user);
}
