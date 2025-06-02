package com.data.repository;

import com.data.model.User;

import java.util.List;

public interface UserRepository {
    List<User> findAll();
    boolean save(User user);
    User findByUsernameAndPassword(String username, String password);
}
