package ru.otus.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тестирование репозитория Книга")
@DataJpaTest
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private TestEntityManager entityManager;
    private static final List<String> WANTED_BOOKS_NAME = List.of("Морфий", "Собачье сердце", "Роковые яйца");

    @Test
    void getById_findBookById() {
        var book = entityManager.find(Book.class, 1L);
        var bookFromRepo = bookRepository.findById(1L);
        assertThat(bookFromRepo).isPresent().get().usingRecursiveComparison().isEqualTo(book);
    }

    @Test
    void getBooks_byAuthorId() {
        List<Book> books = bookRepository.findAllByAuthorId(2L);
        assertThat(books).hasSize(2).allMatch(book -> WANTED_BOOKS_NAME.stream().anyMatch(wb -> book.getName().equals(wb)));
    }

    @Test
    void save_fullSetBook() {
        Genre genre = new Genre(1L, "Стих");
        Author author = new Author(1L, "Михаил", "Лермонтов");
        Book book = new Book(null, "Мцыри", author, genre);

        var savedBook = bookRepository.save(book);
        assertThat(book.getId()).isPositive();
        var searchedBook = bookRepository.findById(savedBook.getId());

        assertThat(searchedBook.get()).isNotNull().matches(b -> b.getName().equals(book.getName())).matches(b -> b.getAuthor() != null).matches(b -> b.getGenre() != null);

    }

    @Test
    void delete_book() {
        var book = entityManager.find(Book.class, 1L);
        assertThat(book).isNotNull();
        entityManager.detach(book);
        bookRepository.deleteById(1L);
        var bookAfterDel = entityManager.find(Book.class, 1L);
        assertThat(bookAfterDel).isNull();

    }

    @Test
    void findAll_AllInfoBooks() {
        SessionFactory sessionFactory = entityManager.getEntityManager().getEntityManagerFactory().unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);
        sessionFactory.getStatistics().clear();

        var books = bookRepository.findAll();

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(1L);
        assertThat(books).isNotNull().hasSize(4).allMatch(b -> !b.getName().equals("")).allMatch(b -> b.getGenre() != null).allMatch(b -> b.getAuthor() != null);

    }

}