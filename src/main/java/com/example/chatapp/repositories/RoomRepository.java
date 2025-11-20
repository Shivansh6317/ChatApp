package com.example.chatapp.repositories;

import com.example.chatapp.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository  extends JpaRepository<Room,String> {
    Room findByRoomId(String roomId);
}
