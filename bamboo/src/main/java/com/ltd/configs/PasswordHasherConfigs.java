/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.configs;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordHasherConfigs {
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String[] users = {
            "lethanhdanh", "tranthib", "leminhc", "phamvand", "vuthie",
            "hoangminhf", "dovang", "buithih", "ngominhi", "trinhvanj"
        };
        String[] passwords = {"123", "123", "123", "123", "123",
                              "123", "123", "123", "123", "123"};

        for (int i = 0; i < users.length; i++) {
            String hashedPassword = passwordEncoder.encode(passwords[i]);
            System.out.println("INSERT INTO User (username, password) VALUES ('" + users[i] + "', '" + hashedPassword + "');");
        }
    }
}
