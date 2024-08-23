/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.controllers;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ltd.pojo.ChatMessage;
import com.ltd.service.FirebaseService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Acer
 */
@RestController
@RequestMapping("/firebase")
public class FirebaseController {

    private final FirebaseService firebaseService;

    @Autowired
    public FirebaseController(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @PostMapping("/saveMessage")
    public ResponseEntity<Void> saveMessage(@RequestParam String message, @RequestParam String sender, @RequestParam String type) {
        // Create a new ChatMessage object
        ChatMessage chatMessage = new ChatMessage(message, sender, ChatMessage.MessageType.valueOf(type.toUpperCase()));
        firebaseService.saveMessage(chatMessage);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getMessages")
    public ResponseEntity<List<ChatMessage>> getMessages() {
        // Fetch messages from Firebase and return them
        List<ChatMessage> messages = firebaseService.getMessages();
        return ResponseEntity.ok(messages);
    }
}
