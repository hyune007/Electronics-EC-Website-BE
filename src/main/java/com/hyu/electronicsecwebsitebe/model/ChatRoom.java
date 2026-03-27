package com.hyu.electronicsecwebsitebe.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "rooms")
public class ChatRoom {

    @Id
    private String id;

    private String roomId;

    private String lastMessage;
    private String lastSender;

    private int unreadCount;

    private LocalDateTime updatedAt;
}
