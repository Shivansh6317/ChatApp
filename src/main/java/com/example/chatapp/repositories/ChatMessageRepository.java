package com.example.chatapp.repositories;
import com.example.chatapp.entities.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository
        extends JpaRepository<ChatMessage, String> {

    Page<ChatMessage> findByRoomIdOrderByTimestampDesc(
            String roomId,
            Pageable pageable
    );
}
