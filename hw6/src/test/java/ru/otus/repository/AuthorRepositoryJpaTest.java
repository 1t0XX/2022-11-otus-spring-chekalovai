package ru.otus.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.entity.Author;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тестирование репозитория Автор")
@DataJpaTest
@Import({AuthorRepositoryJpa.class})
public class AuthorRepositoryJpaTest {

    @Autowired
    private AuthorRepositoryJpa authorRepositoryJpa;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void getById_FindAuthorById() {
        var author = entityManager.find(Author.class, 1L);
        var authorFromRepo = authorRepositoryJpa.getById(1L);
        assertThat(authorFromRepo).isPresent().get().usingRecursiveComparison().isEqualTo(author);
    }

    @Test
    void save_newAuthor() {
        var author = new Author(null, "Тест", "Тест");
        var saveAuthor = authorRepositoryJpa.save(author);

        assertThat(saveAuthor.getId()).isPositive();

        var searchAuthor = authorRepositoryJpa.getById(author.getId());
        assertThat(searchAuthor.get()).isNotNull().matches(a -> a.getName().equals(author.getName())).matches(a -> a.getSurName().equals(author.getSurName()));

    }

    @Test
    void delete_author() {
        var author = entityManager.find(Author.class, 1L);
        assertThat(author).isNotNull();
        entityManager.detach(author);
        authorRepositoryJpa.delete(1L);
        assertThat(entityManager.find(Author.class, 1L)).isNull();
    }

    @Test
    void findAll_AuthorsInfoIn2Queries() {
        SessionFactory sessionFactory = entityManager.getEntityManager().getEntityManagerFactory().unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);
        sessionFactory.getStatistics().clear();

        var authors = authorRepositoryJpa.getAll();
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(1L);
        assertThat(authors).isNotNull().hasSize(3).allMatch(a -> !a.getName().equals("")).allMatch(a -> !a.getSurName().equals(""));

    }

}
