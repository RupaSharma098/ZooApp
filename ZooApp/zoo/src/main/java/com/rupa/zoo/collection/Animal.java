package com.rupa.zoo.collection;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collation = "animals")
@Data
public class Animal {
    @Id
    private String id;

    @NotNull
    String title;

    List<String> favRoom = new ArrayList<>();
    Date created;
    Date updated;
    Date located;
}
