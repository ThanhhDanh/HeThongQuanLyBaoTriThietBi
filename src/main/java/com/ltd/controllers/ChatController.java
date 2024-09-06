/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.controllers;

import com.ltd.pojo.ChatMessage;
import com.ltd.service.FirebaseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Acer
 */
@Controller
public class ChatController {

    private final List<ChatMessage> messages = new ArrayList<>();
    private final FirebaseService firebaseService;

    @Autowired
    public ChatController(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @GetMapping("/chat")
    public String chatPage(Model model) {
        return "chat";
    }

    @PostMapping("/sendMessage")
    @ResponseBody
    public void sendMessage(@RequestParam("message") String message, @RequestParam("type") String type, HttpSession session) {
        String sender = (String) session.getAttribute("username"); // Lấy tên người dùng từ session
        if (sender == null) {
            sender = "Anonymous";
        }
        ChatMessage.MessageType messageType = ChatMessage.MessageType.valueOf(type.toUpperCase());
        ChatMessage chatMessage = new ChatMessage(message, sender, messageType, System.currentTimeMillis());

        // Lưu tin nhắn vào Firebase
        firebaseService.saveMessage(chatMessage);

        // Lưu tin nhắn vào danh sách (tùy chọn, nếu bạn vẫn muốn sử dụng polling)
        messages.add(chatMessage);
    }

    @GetMapping("/pollMessages")
    @ResponseBody
    public List<ChatMessage> pollMessages() {
        List<ChatMessage> currentMessages = new ArrayList<>(messages);
        messages.clear();
        return currentMessages;
    }
}
