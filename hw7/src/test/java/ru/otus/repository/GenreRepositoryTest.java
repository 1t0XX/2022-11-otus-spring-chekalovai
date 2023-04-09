package ru.otus.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.entity.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тестирование репозитория Жанр")
@DataJpaTest
class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void getById_findGenreById() {
        var genre = entityManager.find(Genre.class, 1L);
        var genreFromRepo = genreRepository.findById(1L);
        assertThat(genreFromRepo).isPresent().get().usingRecursiveComparison().isEqualTo(genre);
    }

    @Test
    void save_newGenre() {
        var genre = new Genre(null, "Тест");
        var saveGenre = genreRepository.save(genre);
        assertThat(saveGenre.getId()).isPositive();
        var searchGenre = genreRepository.findById(saveGenre.getId());

        assertThat(searchGenre).isPresent().matches(g -> g.get().getName().equals(genre.getName()));

    }

    @Test
    void delete_genre() {
        var genre = entityManager.find(Genre.class, 1L);
        assertThat(genre).isNotNull();
        entityManager.detach(genre);
        genreRepository.deleteById(1L);
        var searchGenre = entityManager.find(Genre.class, 1L);
        assertThat(searchGenre).isNull();
    }

    @Test
    void findAll_GenresInfoIn2Queries() {
        SessionFactory sessionFactory = entityManager.getEntityManager().getEntityManagerFactory().unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);
        sessionFactory.getStatistics().clear();

        var genres = genreRepository.findAll();

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(1);
        assertThat(genres).isNotNull().hasSize(3).allMatch(b -> !b.getName().equals(""));

    }
}