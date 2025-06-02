package com.data.service;

import com.data.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    boolean save(User user);
    User findByUsernameAndPassword(String username, String password);
}
