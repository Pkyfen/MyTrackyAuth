package ru.mytracky.service;

import ru.mytracky.model.User;

import java.util.List;

public interface UserService {

    User register(User user);

    List<User> getAll();

    User findByUsername(String username);

    User findById(String id);

    void delete(String id);
}
