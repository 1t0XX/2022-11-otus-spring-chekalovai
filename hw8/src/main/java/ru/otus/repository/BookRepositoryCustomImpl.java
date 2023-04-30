package ru.otus.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.model.Book;
import ru.otus.model.CommentBook;

@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    private final MongoTemplate mongoTemplate;


    @Override
    public void deleteByIdCustom(String id) {
        mongoTemplate.remove(Query.query(Criteria.where("book.id").is(id)), CommentBook.class);
        mongoTemplate.remove(Query.query(Criteria.where(id).is(id)), Book.class);
    }
}
