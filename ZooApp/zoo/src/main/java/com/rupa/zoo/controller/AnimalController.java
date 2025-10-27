package com.rupa.zoo.controller;

import com.rupa.zoo.collection.Animal;
import com.rupa.zoo.collection.Room;
import com.rupa.zoo.service.AnimalService;
import com.rupa.zoo.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/animal")
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    @Autowired
    private RoomService roomService;

    /**
     * @param animal
     * @param room
     * @return
     */
    @PostMapping("{room}")
    public ResponseEntity<Animal> createAnimalByRoom(@RequestBody Animal animal, @PathVariable String room){
        try{
            animalService.save(animal, room);
            return new ResponseEntity<>(animal, HttpStatus.CREATED);
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param roomTitle
     * @return
     */
    @GetMapping("{roomTitle}")
    public ResponseEntity<?> getAnimalsOfRoom(@PathVariable String roomTitle){

        Room room = roomService.getRoomByTitle(roomTitle);
        List<Animal> animals = room.getAnimals();
        if(animals != null && !animals.isEmpty()){
            return new ResponseEntity<>(animals, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * @param animalTitle
     * @param roomTitle
     * @return
     */
    @DeleteMapping("{roomTitle}/{animalTitle}")
    public ResponseEntity<?> deleteAnimalByTitle(@PathVariable String animalTitle, @PathVariable String roomTitle){
        animalService.deleteByTitle(animalTitle, roomTitle);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * @param animalTitle
     * @param fromRoom
     * @param toRoom
     * @return
     */
    @PatchMapping("{fromRoom}/{toRoom}/{animalTitle}")
    public ResponseEntity<?> moveAnimal(@PathVariable String animalTitle, @PathVariable String fromRoom, @PathVariable String toRoom){
        animalService.moveAnimal(animalTitle, fromRoom, toRoom);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * @param title
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/search")
    public Page<Animal> searchAnimal(@RequestParam(required = false) String title,
                                     @RequestParam(defaultValue = "0") Integer page,
                                     @RequestParam(defaultValue = "5") Integer size){

        Pageable pageable = PageRequest.of(page, size);
        return animalService.search(title, pageable);
    }

}
