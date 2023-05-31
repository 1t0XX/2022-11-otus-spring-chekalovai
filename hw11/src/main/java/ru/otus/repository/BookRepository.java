package ru.otus.repository;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.model.Book;




public interface BookRepository extends ReactiveMongoRepository<Book, String> {}