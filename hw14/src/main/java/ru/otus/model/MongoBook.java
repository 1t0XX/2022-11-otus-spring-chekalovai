package ru.otus.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(value = "book")
public class MongoBook {

    @Id
    private String id;
    @Field("name")
    private String name;
    @Field("author")
    private MongoAuthor author;
    @Field("genre")
    private MongoGenre genre;

    public static MongoBook toMongoEntity(Book book, String authorId, String genreId) {
        MongoBook mongoBook = new MongoBook(null, book.getName(), MongoAuthor.toMongoEntity(book.getAuthor()),
                MongoGenre.toMongoEntity(book.getGenre()));
        mongoBook.getAuthor().setId(authorId);
        mongoBook.getGenre().setId(genreId);
        return mongoBook;
    }

}
