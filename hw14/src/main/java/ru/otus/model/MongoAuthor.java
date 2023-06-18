package ru.otus.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(value = "author")
public class MongoAuthor implements EntityWithStringId{

    @Id
    private String id;
    @Field("name")
    private String name;
    @Field("surName")
    private String surName;
    @Transient
    private Long oldId;


    public static MongoAuthor toMongoEntity(Author author) {
        return new MongoAuthor(null, author.getName(), author.getSurName(), author.getId());
    }

}
