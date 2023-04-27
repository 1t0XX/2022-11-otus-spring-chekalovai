package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.exception.SaveBookException;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Genre;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentBookRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@DisplayName("Tests for BookService")
@SpringBootTest(classes = {BookServiceImpl.class})
class BookServiceTest {

    @Autowired
    private BookServiceImpl bookService;
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private CommentBookRepository commentRepository;


    private Book createBook(String id) {
        return new Book(id, "Book" + id,
                new Author(id, "name" + id, "lastname" + id),
                new Genre(id, "Genre"));
    }

    @Test
    void findAll_ok() {
        Book book1 = createBook("1");
        Book book2 = createBook("2");
        when(bookRepository.findAll()).thenReturn(List.of(book1, book2));
        List<Book> books = bookService.getAll();
        assertThat(books).hasSize(2).contains(book1, book2);
    }

    @Test
    void getBookById_1() {
        Book book1 = createBook("1");
        when(bookRepository.findById("1")).thenReturn(Optional.of(book1));
        Book book = bookService.getBookById("1");
        assertThat(book.getName()).isEqualTo("Book1");
    }

    @Test
    void saveBook_withIdAndName() {
        Book book1 = createBook("1");
        when(bookRepository.save(book1)).thenReturn(book1);
        Book book = bookService.saveBook(book1);
        assertThat(book.getName()).isEqualTo("Book1");
        assertThat(book.getId()).isEqualTo("1");
    }

    @Test
    void saveBook_Exception() {
        Book book1 = createBook("1");
        when(bookRepository.save(book1)).thenThrow(SaveBookException.class);
        assertThatThrownBy(() -> bookService.saveBook(book1)).isInstanceOf(SaveBookException.class);
    }

    @Test
    void deleteBook_withId_1() {
        doNothing().when(bookRepository).deleteByIdCustom("1");
        bookService.deleteBookById("1");
        verify(bookRepository, times(1)).deleteByIdCustom(anyString());
    }

    @Test
    void getBookByAuthor_withId_1() {
        Book book1 = createBook("1");
        Book book2 = createBook("2");
        when(bookRepository.getByAuthorId("1")).thenReturn(List.of(book1, book2));
        List<Book> books = bookService.getBookByAuthorId("1");
        assertThat(books).contains(book1, book2);
    }

}