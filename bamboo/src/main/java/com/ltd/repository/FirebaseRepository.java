/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ltd.repository;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ValueEventListener;
import com.ltd.pojo.ChatMessage;

/**
 *
 * @author Acer
 */
public interface FirebaseRepository {
    
    void saveMessage(ChatMessage message);
    void getMessage(ValueEventListener listener);
}
