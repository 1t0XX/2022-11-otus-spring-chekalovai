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
import ru.otus.model.Book;
import ru.otus.model.CommentBook;

import java.util.stream.Collectors;

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
        var author = mongoTemplate.findById("1", Author.class);
        var authorFromRepo = authorRepository.findById("1");
        assertThat(authorFromRepo).isPresent().get().usingRecursiveComparison().isEqualTo(author);
    }

    @Test
    void save_newAuthor() {
        var author = new Author("123", "Тест", "Тест");
        var author4Save = authorRepository.save(author);

        assertThat(author4Save.getId()).isNotNull();

        Query query = Query.query(Criteria.where("id").is(author4Save.getId()));
        var author4Find = mongoTemplate.find(query, Author.class);
        assertThat(author4Find.get(0)).isNotNull().matches(a -> a.getName().equals(author.getName()))
                .matches(a -> a.getSurName().equals(author.getSurName()));

    }

    @Test
    void delete_author() {
        var author = mongoTemplate.findById("1", Author.class);
        var book = mongoTemplate.find(
                Query.query(Criteria.where("author").is(author)), Book.class);

        var commentBook = mongoTemplate.find(
                Query.query(Criteria.where("book").in(book)), CommentBook.class);

        assertThat(book).isNotNull().hasSize(3);
        assertThat(commentBook).isNotNull().hasSize(7);

        authorRepository.deleteByIdCustom("1");

        var commentsBookIds = commentBook.stream().map(CommentBook::getId).collect(Collectors.toList());
        var booksIds = book.stream().map(Book::getId).collect(Collectors.toList());

        var commentsAlreadyDel = mongoTemplate.find(
                Query.query(Criteria.where("id").in(commentsBookIds)), CommentBook.class);

        var booksAlreadyDel = mongoTemplate.find(
                Query.query(Criteria.where("id").in(booksIds)), Book.class);

        var authorAlreadyDel = mongoTemplate.findById("1", Author.class);

        assertThat(commentsAlreadyDel).isNotNull().hasSize(0);
        assertThat(booksAlreadyDel).isNotNull().hasSize(0);
        assertThat(authorAlreadyDel).isNull();

    }
}