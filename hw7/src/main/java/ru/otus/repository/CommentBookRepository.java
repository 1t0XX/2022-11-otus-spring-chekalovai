package ru.otus.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.entity.CommentBook;

import java.util.List;


public interface CommentBookRepository extends JpaRepository<CommentBook, Long> {

    @EntityGraph(attributePaths = { "book" }, type = EntityGraph.EntityGraphType.LOAD)
    List<CommentBook> findAllByBookId(Long id);
}