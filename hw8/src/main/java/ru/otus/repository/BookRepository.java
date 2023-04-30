package ru.otus.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.data.repository.query.Param;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Genre;

import java.util.List;


public interface BookRepository extends MongoRepository<Book, String>, BookRepositoryCustom {
    List<Book> getByAuthorId(String id);

    @Query("{ 'author.id' : :#{#authorId} }")
    @Update("{ '$set' : { 'author' : :#{#author} } }")
    void updateAllByAuthorId(@Param("authorId") String authorId, @Param("author") Author author);

    @Query("{ 'genre.id' : :#{#genreId} }")
    @Update("{ '$set' : { 'genre' : :#{#genre} } }")
    void updateAllByGenreId(@Param("genreId") String genreId, @Param("genre") Genre genre);

}