/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ltd.service;

import com.google.firebase.database.ValueEventListener;
import com.ltd.pojo.ChatMessage;
import java.util.List;

/**
 *
 * @author Acer
 */
public interface FirebaseService {
    void saveMessage(ChatMessage message);
    List<ChatMessage> getMessages();
}
