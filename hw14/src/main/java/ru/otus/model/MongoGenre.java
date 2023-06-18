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
@Document(value = "genre")
public class MongoGenre implements EntityWithStringId{

    @Id
    private String id;
    @Field("name")
    private String name;
    @Transient
    private Long oldId;

    public static MongoGenre toMongoEntity(Genre genre) {
        return new MongoGenre(null, genre.getName(), genre.getId());
    }
}
