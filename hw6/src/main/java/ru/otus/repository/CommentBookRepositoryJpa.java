package ru.otus.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.entity.CommentBook;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentBookRepositoryJpa implements CommentBookRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<CommentBook> findAllByBookId(Long id) {
        return entityManager.createQuery("SELECT c FROM CommentBook c join c.book where c.book.id =:id", CommentBook.class)
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public Optional<CommentBook> getById(Long id) {
        return Optional.ofNullable(entityManager.find(CommentBook.class, id));
    }

    @Override
    public CommentBook save(CommentBook commentBook) {
        if (commentBook.getId() != null) {
            return entityManager.merge(commentBook);
        } else {
            entityManager.persist(commentBook);
            return commentBook;
        }
    }

    @Override
    public void delete(Long id) {
        getById(id).ifPresent(entityManager::remove);
    }
}
