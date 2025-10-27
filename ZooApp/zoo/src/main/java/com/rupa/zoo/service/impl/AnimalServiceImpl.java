package com.rupa.zoo.service.impl;

import com.rupa.zoo.collection.Animal;
import com.rupa.zoo.collection.Room;
import com.rupa.zoo.repository.AnimalRepository;
import com.rupa.zoo.service.AnimalService;
import com.rupa.zoo.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AnimalServiceImpl  implements AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private RoomService roomService;

    @Autowired
    private MongoTemplate template;

    @Override
    public Animal findByTitle(String title) {
        return animalRepository.findByTitle(title.toUpperCase());
    }

    /**
     *
     * @param animal
     * @param roomTitle
     */
    @Override
    public void save(Animal animal, String roomTitle) {
        if(null != animal){
            Room room = roomService.getRoomByTitle(roomTitle);
            Date dt = new Date();
            animal.setTitle(animal.getTitle().toUpperCase());
            animal.setCreated(dt);
            animal.setUpdated(dt);
            animal.setLocated(dt);
            Animal saved = animalRepository.save(animal);
            room.getAnimals().add(saved);
            room.setUpdated(dt);
            roomService.save(room);
        }
    }

    /**
     * @param animalTitle
     * @param roomTitle
     */
    @Override
    public void deleteByTitle(String animalTitle, String roomTitle) {
        Room room = roomService.getRoomByTitle(roomTitle.toUpperCase());
        room.getAnimals().removeIf(x -> x.getTitle().equals(animalTitle.toUpperCase()));
        roomService.save(room);
        animalRepository.deleteByTitle(animalTitle.toUpperCase());
    }

    /**
     * @param animalTitle
     * @param fromRoom
     * @param toRoom
     */
    @Override
    public void moveAnimal(String animalTitle, String fromRoom, String toRoom) {


        Animal animal = null;
        Room fRoom = roomService.getRoomByTitle(fromRoom);

        Optional<Animal> optionalAnimal = fRoom.getAnimals().stream().filter(f-> f.getTitle().equalsIgnoreCase(animalTitle)).findFirst();

        if(optionalAnimal.isPresent()){
            animal = optionalAnimal.get();
        }

        fRoom.getAnimals().remove(animal);
        fRoom.setUpdated(new Date());
        roomService.save(fRoom);

        Room tRoom = roomService.getRoomByTitle(toRoom);
        tRoom.getAnimals().add(animal);
        tRoom.setUpdated(new Date());
        roomService.save(tRoom);
    }

    /**
     * @param title
     * @param pageable
     * @return
     */
    @Override
    public Page<Animal> search(String title, Pageable pageable) {

        Query query = new Query().with(pageable);
        List<Criteria> criteria = new ArrayList<>();

        if(null != title && !title.isEmpty()){
            criteria.add(Criteria.where("title").regex(title.toUpperCase(), "i"));
        }
        return null;
    }

}
