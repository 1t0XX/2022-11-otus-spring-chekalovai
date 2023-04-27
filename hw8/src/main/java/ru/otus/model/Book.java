package ru.otus.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    private String id;
    @Field(name = "name")
    private String name;

    @Field(name = "author")
    private Author author;

    @Field(name = "genre")
    private Genre genre;

}