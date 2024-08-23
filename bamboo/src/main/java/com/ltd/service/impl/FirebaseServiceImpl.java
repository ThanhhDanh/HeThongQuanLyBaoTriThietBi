/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.service.impl;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ltd.pojo.ChatMessage;
import com.ltd.repository.FirebaseRepository;
import com.ltd.service.FirebaseService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Acer
 */
@Service
public class FirebaseServiceImpl implements FirebaseService {

    private final DatabaseReference databaseReference;

    @Autowired
    public FirebaseServiceImpl(FirebaseApp firebaseApp) {
        this.databaseReference = FirebaseDatabase.getInstance(firebaseApp).getReference();
    }

    @Override
    public void saveMessage(ChatMessage message) {
        databaseReference.child("messages").push().setValueAsync(message);
    }

    @Override
    public List<ChatMessage> getMessages() {
        final List<ChatMessage> messages = new ArrayList<>();
        databaseReference.child("messages").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    messages.add(snapshot.getValue(ChatMessage.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors
            }
        });
        return messages;
    }
}
