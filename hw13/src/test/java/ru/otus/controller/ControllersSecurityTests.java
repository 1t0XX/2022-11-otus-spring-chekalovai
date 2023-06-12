package ru.otus.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Genre;
import ru.otus.service.BookService;
import ru.otus.service.GenreService;


import java.util.stream.Stream;


import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
public class ControllersSecurityTests {

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    @ParameterizedTest
    @MethodSource("provideEndpointsData")
    void endpointsAreSecured(String path, HttpMethod httpMethod) throws Exception {
        mvc.perform(request(httpMethod, path).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"))
                .andExpect(unauthenticated());
    }

    @WithMockUser(username = "admin", roles = {"MANAGER"})
    @ParameterizedTest
    @MethodSource("generateData4SaveAdminSecurityTests")
    void endpoints4Save_SecuredManager(MockHttpServletRequestBuilder method, Object object) throws Exception {
        mvc.perform(method.with(csrf())
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(object)))
                .andExpect(status().isOk());
    }


    @WithMockUser(username = "admin", roles = {"MANAGER"})
    @ParameterizedTest
    @MethodSource("generateData4DeleteAdminSecurityTests")
    void endpoints4Delete_SecuredManager(MockHttpServletRequestBuilder method) throws Exception {
        mvc.perform(method)
                .andExpect(status().isOk());
    }


    @WithMockUser(username = "user")
    @ParameterizedTest
    @MethodSource("check4userEndpoint")
    void endpointsAreSecuredUser(String path, HttpMethod httpMethod) throws Exception {
        mvc.perform(request(httpMethod, path).with(csrf()))
                .andExpect(status().isForbidden());
    }


    private static Stream<Arguments> check4userEndpoint() {
        var postEndpoints = Stream.of(
                        "/api/author", "/api/genre", "/api/book")
                .map(path -> Arguments.of(path, HttpMethod.POST));

        var deleteEndpoints = Stream.of(
                        "/api/author/1", "/api/genre/1", "/api/book/1")
                .map(path -> Arguments.of(path, HttpMethod.DELETE));
        return Stream.concat(postEndpoints, deleteEndpoints);
    }

    private static Stream<Arguments> provideEndpointsData() {
        var getEndpoints = Stream.of(
                        "/api/author", "/api/genre", "/api/book", "/api/book/1",
                        "/api/author/1", "/api/genre/1")
                .map(path -> Arguments.of(path, HttpMethod.GET));
        var modifyingEndpoints = Stream.of(
                Arguments.of("/api/book", HttpMethod.POST),
                Arguments.of("/api/book/2", HttpMethod.GET),
                Arguments.of("/api/book/2", HttpMethod.DELETE)
        );
        return Stream.concat(getEndpoints, modifyingEndpoints);
    }

    private static Stream<Arguments> generateData4SaveAdminSecurityTests() {
        return Stream.of(
                Arguments.of(post("/api/genre"), createGenre(1L)),
                Arguments.of(post("/api/author"), createAuthor(1L)),
                Arguments.of(post("/api/book"), createBook(1L)));
    }

    private static Stream<Arguments> generateData4DeleteAdminSecurityTests() {
        return Stream.of(
                Arguments.of(delete("/api/genre/1", new Genre())),
                Arguments.of(delete("/api/author/1", new Author())),
                Arguments.of(delete("/api/book/1", new Book())));
    }

    private static Genre createGenre(long id) {
        return new Genre(id, "name" + id);
    }

    private static Book createBook(long id) {
        return new Book(id, "Book" + id,
                new Author(id, "name" + id, "lastname" + id),
                new Genre(id, "Genre"));
    }

    private static Author createAuthor(long id) {
        return new Author(id, "name" + id, "lastname" + id);
    }

}
