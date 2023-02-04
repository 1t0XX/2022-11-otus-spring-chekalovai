package ru.otus.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Tests for AuthorDAOJdbc")
@JdbcTest
@Import({AuthorDAOJdbc.class})
public class AuthorDAOJdbcTest {

    @Autowired
    private AuthorDAOJdbc authorDAOJdbc;

    private static final int ALL_AUTHOR_COUNT = 3;
    private static final String FIND_BY_ID_AUTHOR_LASTNAME = "Лондон";

    @Test
    void getAllAuthor(){
        List<Author> authorList = authorDAOJdbc.getAll();
        assertThat(authorList.size()).isEqualTo(ALL_AUTHOR_COUNT);
    }

    @Test
    void getById_London() {
        Author author = authorDAOJdbc.getById(1L);
        Assertions.assertThat(author.getSurName()).isEqualTo(FIND_BY_ID_AUTHOR_LASTNAME);
    }

    @Test
    void save_newAuthorSaved() {
        Author author = new Author(null, "Test", "Testovich");
        Assertions.assertThat(authorDAOJdbc.getAll().stream().noneMatch(b -> b.getName().equals("Test") && b.getSurName().equals("Testovich"))).isTrue();
        authorDAOJdbc.save(author);
        Assertions.assertThat(authorDAOJdbc.getAll().stream().anyMatch(b -> b.getName().equals("Test") && b.getSurName().equals("Testovich"))).isTrue();

    }
    @Test
    void delete_without_books() {
        Author test = new Author(null, "Test", "Testovich");
        Author savedTest = authorDAOJdbc.save(test);
        Assertions.assertThat(savedTest.getId()).isGreaterThan(0);
        authorDAOJdbc.delete(savedTest.getId());
        assertThatThrownBy(()->authorDAOJdbc.getById(savedTest.getId())).isInstanceOf(EmptyResultDataAccessException.class);
    }

}
