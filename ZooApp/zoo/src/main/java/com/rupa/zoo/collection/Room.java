package com.rupa.zoo.collection;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "rooms")
@Data
public class Room {
    @Id
    String id;

    @Indexed(unique = true)
    @NotNull
    String title;

    Date created;
    Date updated;

    @DBRef
    List<Animal> animals = new ArrayList<>();
}
