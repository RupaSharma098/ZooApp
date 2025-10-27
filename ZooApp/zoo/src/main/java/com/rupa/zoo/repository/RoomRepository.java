package com.rupa.zoo.repository;

import com.rupa.zoo.collection.Room;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends MongoRepository<Room, String> {

    Room findByTitle(String title);

    void deleteAllByTitle(String title);
}
