package com.rupa.zoo.service.impl;

import com.rupa.zoo.collection.Animal;
import com.rupa.zoo.collection.Room;
import com.rupa.zoo.repository.RoomRepository;
import com.rupa.zoo.service.AnimalService;
import com.rupa.zoo.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    private AnimalService animalService;


    /**
     * Save/create a new Room Entry
     *
     * @param room to be saved
     * @return Room which was saved
     */
    @Override
    public Room save(Room room) {

        if(null != room){
            room.setTitle(room.getTitle().toUpperCase());
            room.setCreated(new Date());
            room.setCreated(new Date());
        }
        return roomRepository.save(room);
    }

    /**
     * Retrieve all Rooms
     *
     * @return Room List from Search
     */
    @Override
    public List<Room> getAllRoom() {
        return roomRepository.findAll();
    }

    /**
     * Search Room by room title
     *
     * @param title of Room
     * @return Room from search
     */
    @Override
    public Room getRoomByTitle(String title) {

        return roomRepository.findByTitle(title.toUpperCase());
    }

    /**
     * delete all rooms
     */
    @Override
    public void deleteAll() {
        roomRepository.deleteAll();
    }

    /**
     * delete room by room title
     *
     * @param title of the room
     */
    @Override
    public void deleteRoomByTitle(String title) {
        Room room = roomRepository.findByTitle(title.toUpperCase());
        if(null != room){
            List<Animal> animals = room.getAnimals();
            if(null != animals && !animals.isEmpty()){
                animals.forEach(a ->  animalService.deleteByTitle(a.getTitle(), title));
            }
            roomRepository.deleteAllByTitle(title.toUpperCase());
        }
    }

    /**
     * Finds list of  favourite rooms along with animal count in each favourite room
     *
     * @return  Map of favourite rooms(only ‘title’) for animals and number of animals.
     */
    @Override
    public Map<String, Integer> findFavRooms() {
        List<Room> rooms = getAllRoom();
        List<Animal> animals = new ArrayList<>();
        rooms.stream().filter(f-> !f.getAnimals().isEmpty()).forEach( room -> animals.addAll(room.getAnimals()));
        Map<String, Integer> favRooms= new HashMap<>();

        animals.stream().filter(a-> a.getFavRoom()!=null &&  !a.getFavRoom().isEmpty()).forEach(a-> {
            a.getFavRoom().forEach(fr->{
                if(favRooms.containsKey(fr.toUpperCase())){
                    favRooms.put(fr.toUpperCase(), favRooms.get(fr.toUpperCase())+1 );
                } else{
                    favRooms.put(fr.toUpperCase(), 1 );
                }
            });
        });
        return favRooms;
    }
}
