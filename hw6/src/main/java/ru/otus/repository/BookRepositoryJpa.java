package ru.otus.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.entity.Author;
import ru.otus.entity.Book;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;
import static ru.otus.entity.Book.BOOK_AUTHOR_GENRE_GRAPH;

@Repository
@RequiredArgsConstructor
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<Book> getAll() {
        EntityGraph<?> authorGenreEntityGraph = entityManager.getEntityGraph(BOOK_AUTHOR_GENRE_GRAPH);
        return entityManager.createQuery("select b from Book b", Book.class)
                .setHint(FETCH.getKey(), authorGenreEntityGraph)
                .getResultList();
    }

    @Override
    public Optional<Book> getById(Long id) {
        return Optional.ofNullable(entityManager.find(Book.class, id));
    }

    @Override
    public Book save(Book book) {
        if (book.getId() != null) {
            return entityManager.merge(book);
        } else {
            entityManager.persist(book);
            return book;
        }
    }

    @Override
    public void delete(Long id) {
        getById(id).ifPresent(entityManager::remove);
    }

    @Override
    public List<Book> getByAuthorId(Long id) {
        EntityGraph<?> authorGenreEntityGraph = entityManager.getEntityGraph(BOOK_AUTHOR_GENRE_GRAPH);
        return entityManager.createQuery("select b from Book b where b.author.id = :id", Book.class)
                .setParameter("id", id)
                .setHint(FETCH.getKey(), authorGenreEntityGraph)
                .getResultList();
    }
}
