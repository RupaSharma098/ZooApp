package com.rupa.zoo.repository;

import com.rupa.zoo.collection.Animal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository extends MongoRepository<Animal, String> {

    Animal findByTitle(String title);

    void deleteByTitle(String title);
}
