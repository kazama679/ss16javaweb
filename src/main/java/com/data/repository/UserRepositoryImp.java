package com.data.repository;

import com.data.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImp implements UserRepository {
    @Autowired
    private DataSource dataSource;

    @Override
    public List<User> findAll() {
        Connection conn = null;
        CallableStatement callSt = null;
        List<User> users = new ArrayList<>();
        try{
            conn = dataSource.getConnection();
            callSt = conn.prepareCall("{call display_user()}");
            ResultSet rs = callSt.executeQuery();
            while(rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                user.setStatus(rs.getBoolean("status"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        try (Connection conn = dataSource.getConnection()) {
            CallableStatement call = conn.prepareCall("{call find_user_login(?, ?)}");
            call.setString(1, username);
            call.setString(2, password);
            ResultSet rs = call.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                user.setStatus(rs.getBoolean("status"));
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean save(User user) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = dataSource.getConnection();
            callSt = conn.prepareCall("{call add_user(?,?,?,?,?)}");
            callSt.setString(1, user.getUsername());
            callSt.setString(2, user.getPassword());
            callSt.setString(3, user.getEmail());
            callSt.setString(4, user.getRole());
            callSt.setBoolean(5, user.isStatus());
            callSt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}