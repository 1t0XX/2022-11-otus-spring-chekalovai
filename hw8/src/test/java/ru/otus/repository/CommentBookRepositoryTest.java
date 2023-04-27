package ru.otus.repository;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.model.Book;
import ru.otus.model.CommentBook;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тестирование репозитория Комментарий к книге")
@EnableMongock
@DataMongoTest
class CommentBookRepositoryTest {

    @Autowired
    private CommentBookRepository commentBookRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void findAllByBookId() {
        List<CommentBook> bookComments = commentBookRepository.getAllByBookId("2");
        assertThat(bookComments).hasSize(2).allMatch(b -> StringUtils.isNotBlank(b.getComment()));

    }

    @Test
    void getById_findCommentById() {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is("2"));
        var comments = mongoTemplate.find(query, CommentBook.class);
        var comment = commentBookRepository.findById("2");
        assertThat(comment.get().getId()).isEqualTo(comments.get(0).getId());

    }

    @Test
    void save_newComment() {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is("1"));
        var book = mongoTemplate.find(query, Book.class);
        var comment = new CommentBook(null, "Не интересно", book.get(0));
        var saveComment = commentBookRepository.save(comment);
        assertThat(comment.getId()).isNotNull();
        var searchedComment = commentBookRepository.findById(saveComment.getId());

        assertThat(searchedComment).isPresent()
                .matches(b -> b.get().getComment().equals(comment.getComment()))
                .matches(b -> b.get().getBook() != null);
    }

    @Test
    void deleteComment() {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is("1"));
        var comment2Del = mongoTemplate.find(query, CommentBook.class);
        assertThat(comment2Del).hasSize(1);
        commentBookRepository.deleteById("1");
        assertThat(mongoTemplate.find(query, CommentBook.class)).isEmpty();
    }

}
