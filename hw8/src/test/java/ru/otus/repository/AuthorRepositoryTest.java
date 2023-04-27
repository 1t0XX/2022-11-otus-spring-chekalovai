package ru.otus.repository;

import com.github.cloudyrock.spring.v5.EnableMongock;;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.model.Author;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тестирование репозитория Автор")
@DataMongoTest
@EnableMongock
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void getById_FindAuthorById() {
        Query query = Query.query(Criteria.where("id").is("1"));
        var author = mongoTemplate.find(query, Author.class);
        var authorFromRepo = authorRepository.findById("1");
        assertThat(authorFromRepo).isPresent().get().usingRecursiveComparison().isEqualTo(author.get(0));
    }

    @Test
    void save_newAuthor() {
        var author = new Author("123", "Тест", "Тест");
        var saveAuthor = authorRepository.save(author);

        assertThat(saveAuthor.getId()).isNotNull();

        Query query = Query.query(Criteria.where("id").is(saveAuthor.getId()));
        var searchAuthor = mongoTemplate.find(query, Author.class);
        assertThat(searchAuthor.get(0)).isNotNull().matches(a -> a.getName().equals(author.getName()))
                .matches(a -> a.getSurName().equals(author.getSurName()));

    }

    @Test
    void delete_author() {
        var author = new Author("123", "Тест", "Тест");
        var saveAuthor = authorRepository.save(author);

        Query query = Query.query(Criteria.where("id").is(saveAuthor.getId()));
        var author2Del = mongoTemplate.find(query, Author.class);
        assertThat(author2Del).hasSize(1);
        authorRepository.deleteByIdCustom(author2Del.get(0).getId());
        assertThat(mongoTemplate.find(query, Author.class)).isEmpty();
    }
}