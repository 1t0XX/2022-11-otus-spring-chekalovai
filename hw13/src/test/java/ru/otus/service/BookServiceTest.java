package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.exception.SaveBookException;
import ru.otus.repository.BookRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@DisplayName("Tests for BookService")
@SpringBootTest(classes = {BookServiceImpl.class})
class BookServiceTest {

    @Autowired
    private BookServiceImpl bookService;
    @MockBean
    private BookRepository bookDao;

    @Test
    void findAll_ok() {
        Book book1 = new Book(1L, "Book1", null, null);
        Book book2 = new Book(2L, "Book2", null, null);
        when(bookDao.findAll()).thenReturn(List.of(book1, book2));
        List<Book> books = bookService.getAll();
        assertThat(books).hasSize(2).contains(book1, book2);
    }

    @Test
    void getBookById_1() {
        Book book1 = new Book(1L, "Book1", null, null);
        when(bookDao.findById(1L)).thenReturn(Optional.of(book1));
        Book book = bookService.getBookById(1L);
        assertThat(book.getName()).isEqualTo("Book1");
    }

    @Test
    void saveBook_withIdAndName() {
        Book book1 = new Book(1L, "Book1", null, null);
        when(bookDao.save(book1)).thenReturn(book1);
        Book book = bookService.saveBook(book1);
        assertThat(book.getName()).isEqualTo("Book1");
        assertThat(book.getId()).isEqualTo(1L);
    }

    @Test
    void saveBook_Exception() {
        Book book1 = new Book(1L, "Book1", null, null);
        when(bookDao.save(book1)).thenThrow(SaveBookException.class);
        assertThatThrownBy(() -> bookService.saveBook(book1)).isInstanceOf(SaveBookException.class);
    }

    @Test
    void deleteBook_withId_1() {
        doNothing().when(bookDao).deleteById(1L);
        bookService.deleteBook(1L);
        verify(bookDao, times(1)).deleteById(anyLong());
    }

    @Test
    void getBookByAuthor_withId_1() {
        Book book1 = new Book(1L, "Book1", new Author(1L, null, null), null);
        Book book2 = new Book(2L, "Book2", new Author(1L, null, null), null);
        when(bookDao.findAllByAuthorId(1L)).thenReturn(List.of(book1, book2));
        List<Book> books = bookService.getBookByAuthor(1L);
        assertThat(books).contains(book1, book2);
    }

}