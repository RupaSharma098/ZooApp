package com.rupa.zoo.service;

import com.rupa.zoo.collection.Room;

import java.util.List;
import java.util.Map;

public interface RoomService {
    Room save(Room room);

    List<Room> getAllRoom();

    Room getRoomByTitle(String title);

    void deleteAll();

    void deleteRoomByTitle(String title);

    Map<String, Integer> findFavRooms();
}
