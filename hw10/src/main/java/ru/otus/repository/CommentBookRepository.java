package ru.otus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.model.CommentBook;

import java.util.List;


public interface CommentBookRepository extends JpaRepository<CommentBook, Long> {

    List<CommentBook> findAllByBookId(Long id);
}