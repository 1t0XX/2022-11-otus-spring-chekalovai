package ru.otus.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.model.Author;
import ru.otus.service.AuthorService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AuthorService authorService;


    @Test
    void getAuthorList_return2Author() throws Exception {
        List<Author> authors = createAuthorList(1L);
        when(authorService.getAll()).thenReturn(authors);

        mvc.perform(get("/api/author"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(authors)));
    }

    @Test
    void getAuthorById() throws Exception {
        var author = createAuthor(1L);
        when(authorService.getAuthorById(author.getId())).thenReturn(author);

        mvc.perform(get("/api/author/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(author)));
    }


    private List<Author> createAuthorList(long id) {
        return List.of(new Author(id, "name" + id, "lastname" + id),
                new Author((id + 1), "name" + (id + 1), "lastname" + (id + 1)));
    }

    private Author createAuthor(long id) {
        return new Author(id, "name" + id, "lastname" + id);
    }

}
