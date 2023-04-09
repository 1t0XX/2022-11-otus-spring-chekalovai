package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.entity.Book;
import ru.otus.service.BookService;
import ru.otus.service.MessageService;
import ru.otus.service.WrapperService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class ShellBookController {
    private final BookService bookService;
    private final WrapperService wrapperService;
    private final MessageService messageService;


    @ShellMethod(key = "book.all")
    public List<Book> getAll() {
        return bookService.getAll();
    }

    @ShellMethod(key = "book.byId")
    public Book getBookById(@ShellOption Long bookId) {
        return bookService.getBookById(bookId);
    }

    @ShellMethod(key = "book.save")
    public Book saveBook() {
        return wrapperService.convertInput(
                Book.class,
                "message.book.write",
                bookService::saveBook
        );
    }

    @ShellMethod(key = "book.delete")
    public String deleteBook(@ShellOption Long bookId) {
        bookService.deleteBook(bookId);
        return String.format(messageService.getMessage("message.book.delete"), bookId);
    }

    @ShellMethod(key = "book.byAuthorId")
    public List<Book> getBooksByAuthor(@ShellOption Long authorId) {
        return bookService.getBookByAuthor(authorId);
    }

}
