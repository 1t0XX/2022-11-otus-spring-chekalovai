package ru.otus.repository;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.model.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тестирование репозитория Жанр")
@EnableMongock
@DataMongoTest
class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void getById_findGenreById() {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is("1"));
        var genre = mongoTemplate.find(query, Genre.class);
        var genreFromRepository = genreRepository.findById("1");
        assertThat(genreFromRepository.get().getId()).isEqualTo(genre.get(0).getId());
    }

    @Test
    void save_newGenre() {
        var saveGenre = genreRepository.save(new Genre("6", "6"));
        assertThat(saveGenre.getId()).isNotNull();
        var searchedGenre = genreRepository.findById(saveGenre.getId());

        assertThat(searchedGenre).isPresent()
                .matches(b -> b.get().getName().equals(saveGenre.getName()));

    }

    @Test
    void delete_genre() {
        Genre genreSaved = genreRepository.save(new Genre("7", "777"));
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(genreSaved.getId()));
        var genre2Del = mongoTemplate.find(query, Genre.class);
        assertThat(genre2Del).hasSize(1);
        genreRepository.deleteByIdCustom(genre2Del.get(0).getId());
        assertThat(mongoTemplate.find(query, Genre.class)).isEmpty();
    }
}