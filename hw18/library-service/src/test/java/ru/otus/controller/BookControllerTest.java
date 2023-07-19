package ru.otus.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Genre;
import ru.otus.service.BookService;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookService bookService;

    @Test
    void bookList_return2Books() throws Exception {
        List<Book> books = List.of(createBook(1L), createBook(2L));
        when(bookService.getAll()).thenReturn(books);

        mvc.perform(get("/api/book"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(books)));
    }

    @Test
    void getBook_getBookWithId() throws Exception {
        var book = createBook(1L);
        when(bookService.getBookById(anyLong())).thenReturn(book);

        mvc.perform(get("/api/book/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(book)));

    }

    @Test
    void saveBook_ok() throws Exception {
        Book book = createBook(1L);
        when(bookService.saveBook(book)).thenReturn(book);

        mvc.perform(post("/api/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(book))
                )
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(book)));

    }

    @Test
    void deleteBook_ok() throws Exception {
        Book book = new Book();
        doNothing().when(bookService).deleteBook(anyLong());
        mvc.perform(delete("/api/book/1", book))
                .andExpect(status().isOk());
        verify(bookService, times(1)).deleteBook(1L);

    }

    private Book createBook(long id) {
        return new Book(id, "Book" + id,
                new Author(id, "name" + id, "lastname" + id),
                new Genre(id, "Genre"));
    }

}
