package ru.otus.repository;


import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Genre;


public interface BookRepository extends ReactiveMongoRepository<Book, String> {

    Flux<Book> deleteBooksByAuthorId(String authorId);

    Flux<Book> deleteBooksByGenreId(String genreId);

    @Query("{ 'author.id' : :#{#authorId} }")
    @Update("{ '$set' : { 'author' : :#{#author} } }")
    Mono<Void> updateAllByAuthorId(@Param("authorId") String authorId, @Param("author") Author author);
    @Query("{ 'genre.id' : :#{#genreId} }")
    @Update("{ '$set' : { 'genre' : :#{#genre} } }")
    Mono<Void> updateAllByGenreId(@Param("genreId") String genreId, @Param("genre") Genre genre);

}