package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.model.CommentBook;
import ru.otus.exception.DeleteCommentException;
import ru.otus.exception.FindAllCommentByIdException;
import ru.otus.exception.GetCommentByIdException;
import ru.otus.exception.SaveCommentException;
import ru.otus.repository.CommentBookRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentBookRepository commentBookRepository;

    @Transactional(readOnly = true)
    @Override
    public List<CommentBook> findAllByBookId(Long id) {
        try {
            return commentBookRepository.findAllByBookId(id);
        } catch (Exception e) {
            throw new FindAllCommentByIdException(e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public CommentBook getCommentById(Long id) {
        return commentBookRepository.findById(id).orElseThrow(() -> new GetCommentByIdException(id, "Not found comment by Id"));
    }

    @Transactional
    @Override
    public CommentBook saveComment(CommentBook bookComment) {
        try {
            return commentBookRepository.save(bookComment);
        } catch (Exception e) {
            throw new SaveCommentException(bookComment, e);
        }
    }

    @Transactional
    @Override
    public void deleteComment(Long id) {
        try {
            commentBookRepository.deleteById(id);
        } catch (Exception e) {
            throw new DeleteCommentException(id, e);
        }
    }
}
