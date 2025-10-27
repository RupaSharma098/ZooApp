package com.rupa.zoo.service;

import com.rupa.zoo.collection.Animal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnimalService {
    Animal findByTitle(String title);

    void save(Animal animal, String room);

    void deleteByTitle(String animalTitle, String roomTitle);

    void moveAnimal(String animalTitle, String fromRoom, String toRoom);

    Page<Animal> search(String title, Pageable pageable);

}
