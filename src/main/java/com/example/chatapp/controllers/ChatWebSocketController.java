package com.example.chatapp.controllers;
import com.example.chatapp.dto.ChatMessageDto;
import com.example.chatapp.entities.ChatMessage;
import com.example.chatapp.repositories.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final ChatMessageRepository repository;

    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public ChatMessage send(
            @DestinationVariable String roomId,
            @Payload ChatMessageDto dto
    ) {
        ChatMessage message = new ChatMessage();
        message.setRoomId(roomId);
        message.setSender(dto.getSender());
        message.setContent(dto.getContent());
        message.setTimestamp(LocalDateTime.now());

        repository.save(message);
        return message;
    }
}
