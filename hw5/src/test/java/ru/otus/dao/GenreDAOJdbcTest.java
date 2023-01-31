package ru.otus.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
@Import({GenreDAOJdbc.class})
public class GenreDAOJdbcTest {

    @Autowired
    private GenreDAOJdbc genreDao;

    private static final int EXPECTED_ALL_GENRE_COUNT = 3;
    private static final String EXPECTED_FIND_BY_ID_GENRE_NAME = "Повесть";

    @Test
    void findAll_find3Genres() {
        List<Genre> genres = genreDao.getAll();
        assertThat(genres.size()).isEqualTo(EXPECTED_ALL_GENRE_COUNT);
    }

    @Test
    void getById_Povest() {
        Genre genre = genreDao.getById(1L);
        assertThat(genre.getName()).isEqualTo(EXPECTED_FIND_BY_ID_GENRE_NAME);
    }

    @Test
    void save_newGenreSaved() {
        Genre genre = new Genre(null, "Test");
        assertThat(genreDao.getAll().stream().anyMatch(b -> b.getName().equals("Test"))).isFalse();
        Genre genre1 = genreDao.save(genre);
        List<Genre> genres = genreDao.getAll();
        long count = genreDao.getAll().stream().filter(b -> b.getName().equals("Test")).count();
        assertThat(count).isEqualTo(1);

    }

    @Test
    void delete_without_books() {
        Genre test = new Genre(null, "Test");
        Genre savedTest = genreDao.save(test);
        assertThat(savedTest.getId()).isGreaterThan(0);
        genreDao.delete(savedTest.getId());
        assertThatThrownBy(()->genreDao.getById(savedTest.getId())).isInstanceOf(EmptyResultDataAccessException.class);
    }
}
