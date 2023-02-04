package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Tests for BookDAOJdbcTest")
@JdbcTest
@Import({BookDAOJdbc.class, AuthorDAOJdbc.class})
public class BookDAOJdbcTest {

    @Autowired
    private BookDAOJdbc bookDao;

    @MockBean
    private AuthorDAO authorDao;

    private static final int EXPECTED_ALL_BOOK_COUNT = 4;
    private static final String EXPECTED_FIND_BY_ID_BOOK_NAME = "Лезвие бритвы";

    @Test
    void findAll_find4Books() {
        List<Book> books = bookDao.getAll();
        assertThat(books.size()).isEqualTo(EXPECTED_ALL_BOOK_COUNT);
    }

    @Test
    void getById_lezvieBritviBook() {
        Book book = bookDao.getById(1l);
        assertThat(book.getName()).isEqualTo(EXPECTED_FIND_BY_ID_BOOK_NAME);
    }

    @Test
    void getByAuthor_id_1() {
        List<Book> books = bookDao.getByAuthorId(1l);
        assertThat(books.size()).isEqualTo(1);
    }

    @Test
    void save_newBookSaved() {
        Book book = new Book(null, "Test book", new Author(1l,null,null), new Genre(1l,null));
        assertThat(bookDao.getAll().stream().noneMatch(b -> b.getName().equals("Test book"))).isTrue();
        bookDao.save(book);
        assertThat(bookDao.getAll().stream().anyMatch(b -> b.getName().equals("Test book"))).isTrue();
    }

    @Test
    void save_bookUpdated() {
        Book book = bookDao.getById(1l);
        assertThat(
                book.getName().equals("Лезвие бритвы") &&
                        book.getAuthor().getId() == 3 &&
                        book.getGenre().getId() == 2

        ).isTrue();
        book.setGenre(new Genre(1l, null));
        book.setAuthor(new Author(1l, null,null));
        bookDao.save(book);
        assertThat(
                book.getName().equals("Лезвие бритвы") &&
                        book.getAuthor().getId() == 1 &&
                        book.getGenre().getId() == 1

        ).isTrue();
    }

    @Test
    void delete() {
        Book book = bookDao.getById(1l);
        assertThat(book).isNotNull();
        bookDao.delete(book.getId());
        assertThatThrownBy(() -> bookDao.getById(1l));
    }
}
