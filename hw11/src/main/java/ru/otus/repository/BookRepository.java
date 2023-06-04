package ru.otus.repository;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.otus.model.Book;


public interface BookRepository extends ReactiveMongoRepository<Book, String> {

    Flux<Book> deleteBooksByAuthorId(String authorId);

    Flux<Book> deleteBooksByGenreId(String genreId);

    Flux<Book> findBooksByAuthorId(String authorId);

}