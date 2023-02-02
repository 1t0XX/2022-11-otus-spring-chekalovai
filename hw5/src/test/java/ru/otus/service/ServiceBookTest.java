package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.dao.BookDAO;
import ru.otus.domain.Book;
import ru.otus.domain.Author;
import ru.otus.exception.service.SaveBookException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@DisplayName("Tests for ServiceBook")
@SpringBootTest(classes = {BookServiceImpl.class})
public class ServiceBookTest {

    @Autowired
    private BookServiceImpl bookService;
    @MockBean
    private BookDAO bookDao;

    @Test
    void findAll_ok() {
        Book book1 = new Book(1L, "Book1");
        Book book2 = new Book(2L, "Book2");
        when(bookDao.getAll()).thenReturn(List.of(book1, book2));
        List<Book> books = bookService.getAll();
        assertThat(books.size()).isEqualTo(2);
        assertThat(books).contains(book1,book2);
    }

    @Test
    void getBookById_1() {
        Book book1 = new Book(1L, "Book1");
        when(bookDao.getById(1L)).thenReturn(book1);
        Book book = bookService.getBookById(1L);
        assertThat(book.getName()).isEqualTo("Book1");
    }

    @Test
    void saveBook_withIdAndName() {
        Book book1 = new Book(1L, "Book1");
        when(bookDao.save(book1)).thenReturn(book1);
        Book book = bookService.saveBook(book1);
        assertThat(book.getName()).isEqualTo("Book1");
        assertThat(book.getId()).isEqualTo(1L);
    }

    @Test
    void saveBook_Exception() {
        Book book1 = new Book(1L, "Book1");
        when(bookDao.save(book1)).thenThrow(SaveBookException.class);
        assertThatThrownBy(() -> bookService.saveBook(book1)).isInstanceOf(SaveBookException.class);
    }

    @Test
    void deleteBook_withId_1() {
        doNothing().when(bookDao).delete(1L);
        bookService.deleteBook(1L);
        verify(bookDao,times(1)).delete(anyLong());
    }
    @Test
    void getBookByAuthor_withId_1() {
        Book book1 = new Book(1L, "Book1", new Author(1L,null,null), null);
        Book book2 = new Book(2L, "Book2", new Author(1L,null,null), null);
        when(bookDao.getByAuthorId(1L)).thenReturn(List.of(book1,book2));
        List<Book> books = bookService.getBookByAuthor(1L);
        assertThat(books).contains(book1, book2);
    }

}
