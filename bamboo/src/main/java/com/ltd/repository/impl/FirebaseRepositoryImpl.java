/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.repository.impl;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ltd.pojo.ChatMessage;
import com.ltd.repository.FirebaseRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Acer
 */
@Repository
public class FirebaseRepositoryImpl implements FirebaseRepository {

    private final DatabaseReference databaseReference;

    public FirebaseRepositoryImpl(FirebaseApp firebaseApp) {
        this.databaseReference = FirebaseDatabase.getInstance(firebaseApp).getReference();
    }

    @Override
    public void saveMessage(ChatMessage message) {
        databaseReference.child("messages").push().setValueAsync(message);
    }

    @Override
    public void getMessage(ValueEventListener listener) {
        databaseReference.child("messages").addValueEventListener(listener);
    }
}
