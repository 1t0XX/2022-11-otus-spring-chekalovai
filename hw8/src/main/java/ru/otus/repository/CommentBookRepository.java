package ru.otus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.model.CommentBook;

import java.util.List;

public interface CommentBookRepository extends MongoRepository<CommentBook, String> {
    List<CommentBook> getAllByBookId(String id);
}
