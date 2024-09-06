/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ltd.repository;

import com.ltd.pojo.User;
import java.util.List;

/**
 *
 * @author Acer
 */
public interface UserRepository {
    User getUserByUsername(String username);
    List<User> getAllUsers();          
    List<User> getUsersByRole(String role);
    boolean authUser(String username, String password);
    User addUser(User user);
}
