package com.hyu.electronicsecwebsitebe.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "messages")
public class ChatMessage {

    @Id
    private String id;

    private String roomId;
    private String senderName;
    private String senderType;
    private String content;

    private boolean seen;
    private LocalDateTime timestamp;
}
