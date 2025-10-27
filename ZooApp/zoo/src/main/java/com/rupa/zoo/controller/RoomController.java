package com.rupa.zoo.controller;

import com.rupa.zoo.collection.Room;
import com.rupa.zoo.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    /**
     * @param room
     * @return
     */
    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody Room room){
        try{
            roomService.save(room);
            return new ResponseEntity<>(room, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @return
     */
    @GetMapping
    public ResponseEntity<?> getAllRoom(){
        List<Room> rooms = roomService.getAllRoom();
        if(rooms != null && !rooms.isEmpty()){
            return new ResponseEntity<>(rooms, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * @param title
     * @return
     */
    @DeleteMapping("{title}")
    public ResponseEntity<?> deleteRoomByName(@PathVariable String title){
        roomService.deleteRoomByTitle(title);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * @return
     */
    @GetMapping("/favRooms")
    public ResponseEntity<Map<String, Integer>> listFavouriteRooms() {
        try {
            Map<String, Integer> favRooms = roomService.findFavRooms();
            return new ResponseEntity<>(favRooms, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
