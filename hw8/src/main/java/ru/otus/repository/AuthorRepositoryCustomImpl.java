package ru.otus.repository;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.CommentBook;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

@RequiredArgsConstructor
public class AuthorRepositoryCustomImpl implements AuthorRepositoryCustom {

    @Data
    private static class BookId {
        private final String bookId;
    }

    private final MongoTemplate mongoTemplate;

    @Override
    public void deleteByIdCustom(String id) {
        var aggregation = Aggregation.newAggregation(match(Criteria.where("author.id").is(id)),
                project("id").and("id").as("bookId"));
        mongoTemplate.aggregate(aggregation, Book.class, BookId.class)
                .getMappedResults().forEach(
                        bookId -> mongoTemplate.remove(
                                Query.query(Criteria.where("book.id").is(bookId.getBookId())), CommentBook.class));
        mongoTemplate.remove(Query.query(Criteria.where("author.id").is(id)), Book.class);
        mongoTemplate.remove(Query.query(Criteria.where("id").is(id)), Author.class);
    }
}
