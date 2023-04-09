package ru.otus.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.entity.Book;
import ru.otus.entity.CommentBook;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тестирование репозитория Комментарий к книге")
@DataJpaTest
@Import({CommentBookRepositoryJpa.class})
public class CommentBookRepositoryJpaTest {

    @Autowired
    private CommentBookRepositoryJpa commentBookRepositoryJpa;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void findAllByBookId() {
        List<CommentBook> bookComments = commentBookRepositoryJpa.findAllByBookId(1L);
        assertThat(bookComments).hasSize(2).allMatch(b -> StringUtils.isNotBlank(b.getComment()));

    }

    @Test
    void getById_findCommentById() {
        var comment = entityManager.find(CommentBook.class, 1L);
        var commentFromRepo = commentBookRepositoryJpa.getById(1L);
        assertThat(commentFromRepo).isPresent().get().usingRecursiveComparison().isEqualTo(comment);

    }

    @Test
    void save_newComment() {
        var book = entityManager.find(Book.class, 1L);
        var comment = new CommentBook(null, "Тест", book);
        var saveComment = commentBookRepositoryJpa.save(comment);
        assertThat(comment.getId()).isPositive();
        var searchedComment = commentBookRepositoryJpa.getById(saveComment.getId());

        assertThat(searchedComment).isPresent().matches(b -> b.get().getComment().equals(comment.getComment())).matches(b -> b.get().getBook() != null);
    }

    @Test
    void deleteComment() {
        var comment = entityManager.find(CommentBook.class, 1L);
        assertThat(comment).isNotNull();
        entityManager.detach(comment);
        commentBookRepositoryJpa.delete(1L);
        var commentAfterDel = entityManager.find(CommentBook.class, 1L);
        assertThat(commentAfterDel).isNull();

    }

}
