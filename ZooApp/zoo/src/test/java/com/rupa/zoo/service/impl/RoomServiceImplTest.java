package com.rupa.zoo.service.impl;

import com.rupa.zoo.collection.Animal;
import com.rupa.zoo.collection.Room;
import com.rupa.zoo.repository.RoomRepository;
import com.rupa.zoo.service.AnimalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoomServiceImplTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private AnimalService animalService;

    @InjectMocks
    private RoomServiceImpl roomService;

    @Test
    void testSave(){
        Room room = createRoom("Green");
        when(roomRepository.save(room)).thenReturn(room);
        Room room1 = roomService.save(room);
        assertEquals(room.getId(), room1.getId());
    }

    @Test
    void testGetAllRoom(){
        List<Room> rooms = getRooms();
        when(roomRepository.findAll()).thenReturn(rooms);
        List<Room> roomList = roomService.getAllRoom();

        assertEquals(1, roomList.size());

    }

    @Test
    void testGetRoomByTitle(){

        Room room = createRoom("GRERN");

        when(roomRepository.findByTitle("GRERN")).thenReturn(room);
        Room resultRoom = roomService.getRoomByTitle("GRERN");

        assertEquals(room.getTitle(), resultRoom.getTitle());

    }

    @Test
    void testDeleteAll(){
        doNothing().when(roomRepository).deleteAll();
        roomService.deleteAll();
        verify(roomRepository, times(1)).deleteAll();
    }

    @Test
    void testDeleteRoomByTitle(){
        String title = "GREEN";
        Room room = createRoom(title);

        List<Animal> animalList = getAnimals();

        room.setAnimals(animalList);

        when(roomRepository.findByTitle(title)).thenReturn(room);

        doNothing().when(animalService).deleteByTitle(anyString() ,anyString());

        doNothing().when(roomRepository).deleteAllByTitle(title);
        roomService.deleteRoomByTitle(title);
        verify(roomRepository, times(1)).deleteAllByTitle(title);
    }


    @Test
    void testFindFavRooms(){
        List<Room> rooms = getRooms();
        when(roomRepository.findAll()).thenReturn(rooms);
        Map<String, Integer> favRooms =  roomService.findFavRooms();
        assertEquals(0, favRooms.size());

    }

    Room createRoom(String title){
        Room room = new Room();
        room.setTitle(title);
        room.setAnimals(getAnimals());
        return room;
    }

    Animal createAnimal(String title){
        Animal animal = new Animal();
        animal.setTitle(title);
        return animal;
    }

    List<Animal> getAnimals(){
        List<Animal> animals = new ArrayList<>();
        animals.add(createAnimal("TIGER"));
        animals.add(createAnimal("MONKEY"));
        return animals;
    }

    List<Room> getRooms(){
        List<Room> rooms = new ArrayList<>();
        rooms.add(createRoom("GREEN"));
        return rooms;
    }
}

