package com.wjj.dao;

import java.util.List;

import com.wjj.entity.User;

public interface UserDao {

    void addUser(User user);
    
    void deleteUser(int id);
    
    void updateUser(User user);
    
    User getUserById(int id);
    
    User getUserByName(String username);
    
    User getUserByLevel(int level);
    
    List<User> getAllUsers();
}