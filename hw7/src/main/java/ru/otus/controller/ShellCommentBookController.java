package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.entity.CommentBook;
import ru.otus.service.CommentService;
import ru.otus.service.MessageService;
import ru.otus.service.WrapperService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommentBookController {

    private final CommentService commentService;

    private final MessageService messageService;

    private final WrapperService wrapperService;

    @ShellMethod(key = "comment.allByBookId")
    public List<CommentBook> getAllCommentByBookId(@ShellOption Long bookId) {
        return commentService.findAllByBookId(bookId);
    }

    @ShellMethod(key = "comment.byId")
    public CommentBook getCommentById(@ShellOption Long commentId) {
        return commentService.getCommentById(commentId);
    }

    @ShellMethod(key = "comment.save")
    public CommentBook saveComment() {
        return wrapperService.convertInput(
                CommentBook.class,
                "message.comment.write",
                commentService::saveComment
        );
    }

    @ShellMethod(key = "comment.delete")
    public String deleteComment(@ShellOption Long commentId) {
        commentService.deleteComment(commentId);
        return String.format(messageService.getMessage("message.comment.delete"), commentId);
    }
}
