package com.hyu.electronicsecwebsitebe.controller;


import com.hyu.electronicsecwebsitebe.model.ChatMessage;
import com.hyu.electronicsecwebsitebe.model.ChatRoom;
import com.hyu.electronicsecwebsitebe.repository.ChatMessageRepository;
import com.hyu.electronicsecwebsitebe.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatRestController {

    @Autowired
    private ChatMessageRepository messageRepo;

    @Autowired
    private ChatRoomRepository roomRepo;

    @GetMapping("/{roomId}")
    public List<ChatMessage> getMessages(@PathVariable String roomId) {
        return messageRepo.findByRoomIdOrderByTimestampAsc(roomId);
    }

    @PutMapping("/seen/{roomId}")
    public void markAsSeen(@PathVariable String roomId) {

        List<ChatMessage> messages = messageRepo.findByRoomIdOrderByTimestampAsc(roomId);

        for (ChatMessage msg : messages) {
            if (!msg.isSeen()) {
                msg.setSeen(true);
            }
        }

        messageRepo.saveAll(messages);


        ChatRoom room = roomRepo.findByRoomId(roomId).orElse(null);
        if (room != null) {
            room.setUnreadCount(0);
            roomRepo.save(room);
        }
    }
}
