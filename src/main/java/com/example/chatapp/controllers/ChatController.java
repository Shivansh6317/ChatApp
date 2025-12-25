package com.example.chatapp.controllers;

import com.example.chatapp.entities.Message;
import com.example.chatapp.entities.Room;
import com.example.chatapp.playload.MessageRequest;
import com.example.chatapp.repositories.RoomRepository;
import org.aspectj.bridge.IMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/api/")
@CrossOrigin("http://localhost:3000")
public class ChatController {
    private RoomRepository roomRepository;
    public ChatController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }
    @MessageMapping("/sendMessage/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public Message sendMessage(
            @DestinationVariable String roomId,
            @RequestBody MessageRequest request)
    {
        Room room=roomRepository.findByRoomId(request.getRoomId());
        Message message=new Message();
        message.setContent(request.getContent());
        message.setSender(request.getSender());
        message.setTimeStamp(LocalDateTime.now());
        if(room!=null){
            room.getMessage().add(message);
            roomRepository.save(room);
        }else
        {
            throw new RuntimeException("room not found !");
        }
        return message;
    }
}
