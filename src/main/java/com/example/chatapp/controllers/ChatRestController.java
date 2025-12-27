package com.example.chatapp.controllers;
import com.example.chatapp.entities.ChatMessage;
import com.example.chatapp.repositories.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@CrossOrigin
public class ChatRestController {

    private final ChatMessageRepository repository;

    @GetMapping("/{roomId}/messages")
    public Page<ChatMessage> getMessages(
            @PathVariable String roomId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return repository.findByRoomIdOrderByTimestampDesc(
                roomId,
                PageRequest.of(page, size)
        );
    }
}
