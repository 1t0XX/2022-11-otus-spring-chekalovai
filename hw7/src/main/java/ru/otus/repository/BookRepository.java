package ru.otus.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.entity.Book;

import java.util.List;
import java.util.Optional;

import static ru.otus.entity.Book.BOOK_AUTHOR_GENRE_GRAPH;


public interface BookRepository extends JpaRepository<Book, Long> {
    @EntityGraph(value = BOOK_AUTHOR_GENRE_GRAPH, type = EntityGraph.EntityGraphType.LOAD)
    List<Book> findAllByAuthorId(Long id);

    @EntityGraph(value = BOOK_AUTHOR_GENRE_GRAPH, type = EntityGraph.EntityGraphType.LOAD)
    @Override
    List<Book> findAll();

    @EntityGraph(value = BOOK_AUTHOR_GENRE_GRAPH, type = EntityGraph.EntityGraphType.LOAD)
    @Override
    Optional<Book> findById(Long id);
}