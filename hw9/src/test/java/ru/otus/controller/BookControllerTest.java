package ru.otus.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Genre;
import ru.otus.service.AuthorService;
import ru.otus.service.BookService;
import ru.otus.service.FormDataFormatter;
import ru.otus.service.GenreService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean(name = "authorServiceImpl")
    private AuthorService authorService;

    @MockBean(name = "genreServiceImpl")
    private GenreService genreService;

    @MockBean
    private BookService bookService;

    @MockBean(name = "formatter")
    private FormDataFormatter formatter;

    @Test
    void bookList_return2Books() throws Exception {
        List<Book> books = List.of(createBook(1L), createBook(2L));
        when(bookService.getAll()).thenReturn(books);
        when(formatter.authorFormat(any())).thenReturn("SomeAuthor");

        mvc.perform(get("/book"))
                .andExpect(status().isOk())
                .andExpect(view().name("book-list"))
                .andExpect(model().attributeExists("books"));
    }

    @Test
    void editBook_getBookWithId() throws Exception {
        var book = createBook(1L);
        when(bookService.getBookById(anyLong())).thenReturn(book);
        when(authorService.getAll()).thenReturn(createAuthorList(11L));
        when(genreService.getAll()).thenReturn(createGenreList(11L));

        mvc.perform(get("/book/1?readOnly=false"))
                .andExpect(status().isOk())
                .andExpect(view().name("book-edit"))
                .andExpect(model().attributeExists("book", "readOnly"))
                .andExpect(model().attribute("book", book));

    }

    @Test
    void editBook_createNew() throws Exception {
        when(authorService.getAll()).thenReturn(createAuthorList(11L));
        when(genreService.getAll()).thenReturn(createGenreList(11L));

        mvc.perform(get("/book/0?readOnly=false"))
                .andExpect(status().isOk())
                .andExpect(view().name("book-edit"))
                .andExpect(model().attributeExists("book", "readOnly"));
    }

    @Test
    void saveBook_notValidBookError() throws Exception {
        var book = new Book();
        mvc.perform(post("/book", book))
                .andExpect(status().isOk())
                .andExpect(view().name("book-edit"));
    }

    @Test
    void saveBook_ok() throws Exception {
        Book book = createBook(1L);
        when(bookService.saveBook(book)).thenReturn(book);

        mvc.perform(post("/book")
                        .param("id", "1")
                        .param("name", "test")
                        .param("author.id", "1")
                        .param("author.id", "1")
                        .param("genre.id", "1")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/book"));

    }

    @Test
    void deleteBook_ok() throws Exception {
        Book book = new Book();
        doNothing().when(bookService).deleteBook(anyLong());
        mvc.perform(delete("/book/1", book))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/book"));

    }

    private Book createBook(long id) {
        return new Book(id, "Book" + id,
                new Author(id, "name" + id, "lastname" + id),
                new Genre(id, "Genre"));
    }

    private List<Author> createAuthorList(long id) {
        return List.of(new Author(id, "name" + id, "lastname" + id),
                new Author((id + 1), "name" + (id + 1), "lastname" + (id + 1)));
    }

    private List<Genre> createGenreList(long id) {
        return List.of(new Genre(id, "Genre"),
                new Genre(id + 1, "Genre"));
    }

}
