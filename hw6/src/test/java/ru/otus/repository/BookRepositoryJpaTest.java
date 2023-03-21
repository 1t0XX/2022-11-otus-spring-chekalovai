package ru.otus.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.entity.Author;
import ru.otus.entity.Book;
import ru.otus.entity.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тестирование репозитория Книга")
@DataJpaTest
@Import({BookRepositoryJpa.class})
public class BookRepositoryJpaTest {

    @Autowired
    private BookRepositoryJpa bookRepositoryJpa;

    @Autowired
    private TestEntityManager entityManager;

    private static final List<String> WANTED_BOOKS_NAME = List.of("Морфий", "Собачье сердце", "Роковые яйца");

    @Test
    void getById_findBookById() {
        var book = entityManager.find(Book.class, 1L);
        var bookFromRepo = bookRepositoryJpa.getById(1L);
        assertThat(bookFromRepo).isPresent().get().usingRecursiveComparison().isEqualTo(book);
    }

    @Test
    void getBooks_byAuthorId() {
        List<Book> books = bookRepositoryJpa.getByAuthorId(2L);
        assertThat(books).hasSize(2).allMatch(book -> WANTED_BOOKS_NAME.stream().anyMatch(wb -> book.getName().equals(wb)));
    }

    @Test
    void save_fullSetBook() {
        Genre genre = new Genre(null, "Стих");
        Author author = new Author(null, "Михаил", "Лермонтов");
        Book book = new Book(null, "Мцыри", author, genre);

        var savedBook = bookRepositoryJpa.save(book);
        assertThat(book.getId()).isPositive();
        var searchedBook = bookRepositoryJpa.getById(savedBook.getId());

        assertThat(searchedBook.get()).isNotNull().matches(b -> b.getName().equals(book.getName())).matches(b -> b.getAuthor() != null).matches(b -> b.getGenre() != null);

    }

    @Test
    void delete_book() {
        var book = entityManager.find(Book.class, 1L);
        assertThat(book).isNotNull();
        entityManager.detach(book);
        bookRepositoryJpa.delete(1L);
        var bookAfterDel = entityManager.find(Book.class, 1L);
        assertThat(bookAfterDel).isNull();

    }

    @Test
    void findAll_AllInfoBooks() {
        SessionFactory sessionFactory = entityManager.getEntityManager().getEntityManagerFactory().unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);
        sessionFactory.getStatistics().clear();

        var books = bookRepositoryJpa.getAll();

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(1L);
        assertThat(books).isNotNull().hasSize(4).allMatch(b -> !b.getName().equals("")).allMatch(b -> b.getGenre() != null).allMatch(b -> b.getAuthor() != null);

    }

}
