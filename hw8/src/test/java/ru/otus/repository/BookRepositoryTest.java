package ru.otus.repository;


import com.github.cloudyrock.spring.v5.EnableMongock;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тестирование репозитория Книга")
@EnableMongock
@DataMongoTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MongoTemplate mongoTemplate;
    private static final List<String> WANTED_BOOKS_NAME1 = List.of("22222", "44444", "55555");
    private static final List<String> WANTED_BOOKS_NAME2 = List.of("22222", "33333", "55555");

    private Book getFullBook(String id) {
        Genre genre = new Genre(id, id.repeat(4));
        Author author = new Author(id, id.repeat(3), id.repeat(3));
        return new Book(id, id.repeat(2), author, genre);
    }

    @Test
    void getById_findBookById() {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is("1"));
        var book = mongoTemplate.find(query, Book.class);
        var bookFromRepo = bookRepository.findById("1");
        assertThat(bookFromRepo.get().getId()).isEqualTo(book.get(0).getId());
    }

    @Test
    void getBooks_byAuthorId() {
        List<Book> books = bookRepository.getByAuthorId("1");
        assertThat(books).hasSize(3).allMatch(book -> WANTED_BOOKS_NAME1.stream().anyMatch(wb -> book.getName().equals(wb)));
    }

    @Test
    void save_fullSetBook() {
        var savedBook = bookRepository.save(getFullBook("7"));
        assertThat(savedBook.getId()).isNotNull();
        var searchedBook = bookRepository.findById(savedBook.getId());

        Assertions.assertThat(searchedBook.get()).isNotNull()
                .matches(b -> b.getName().equals(savedBook.getName()))
                .matches(b -> b.getAuthor() != null)
                .matches(b -> b.getGenre() != null);
    }

    @Test
    void delete_book() {
        Book book2Save = bookRepository.save(getFullBook("8"));
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(book2Save.getId()));
        var book2Del = mongoTemplate.find(query, Book.class);
        assertThat(book2Del).hasSize(1);
        bookRepository.deleteById(book2Del.get(0).getId());
        assertThat(mongoTemplate.find(query, Book.class)).isEmpty();

    }

}