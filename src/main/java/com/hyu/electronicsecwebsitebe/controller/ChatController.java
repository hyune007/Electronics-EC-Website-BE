package com.hyu.electronicsecwebsitebe.controller;


import com.hyu.electronicsecwebsitebe.model.ChatMessage;
import com.hyu.electronicsecwebsitebe.model.ChatRoom;
import com.hyu.electronicsecwebsitebe.repository.ChatMessageRepository;
import com.hyu.electronicsecwebsitebe.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatMessageRepository messageRepo;

    @Autowired
    private ChatRoomRepository roomRepo;

    @MessageMapping("/chat.send")
    public void send(ChatMessage message) {

        message.setTimestamp(LocalDateTime.now());
        message.setSeen(false);

        // 💾 1. lưu message
        messageRepo.save(message);

        // 📦 2. update room
        ChatRoom room = roomRepo.findByRoomId(message.getRoomId())
                .orElseGet(() -> {
                    ChatRoom r = new ChatRoom();
                    r.setRoomId(message.getRoomId());
                    r.setUnreadCount(0);
                    return r;
                });

        room.setLastMessage(message.getContent());
        room.setLastSender(message.getSenderName());
        room.setUpdatedAt(LocalDateTime.now());

        // 👉 nếu customer gửi → tăng unread
        if ("CUSTOMER".equals(message.getSenderType())) {
            room.setUnreadCount(room.getUnreadCount() + 1);
        }
        if ("STAFF".equals(message.getSenderType())) {
            room.setUnreadCount(0);
        }
        roomRepo.save(room);

        // 📡 3. realtime
        messagingTemplate.convertAndSend("/topic/admin", message);
        messagingTemplate.convertAndSend("/topic/chat/" + message.getRoomId(), message);
    }
}