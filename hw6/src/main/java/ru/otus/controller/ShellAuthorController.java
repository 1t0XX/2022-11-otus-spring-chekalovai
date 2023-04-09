package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.entity.Author;
import ru.otus.service.AuthorService;
import ru.otus.service.MessageService;
import ru.otus.service.WrapperService;

import java.util.List;


@ShellComponent
@RequiredArgsConstructor
public class ShellAuthorController {
    private final AuthorService authorService;
    private final WrapperService wrapperService;
    private final MessageService messageService;

    @ShellMethod(key = "author.all")
    public List<Author> getAll() {
        return authorService.getAll();
    }

    @ShellMethod(key = "author.byId")
    public Author getAuthorById(@ShellOption Long authorId) {
        return authorService.getAuthorById(authorId);
    }

    @ShellMethod(key = "author.save")
    public Author saveAuthor() {
        return wrapperService.convertInput(
                Author.class,
                "message.author.write",
                authorService::saveAuthor
        );
    }

    @ShellMethod(key = "author.delete")
    public String deleteAuthor(@ShellOption Long authorId) {
        authorService.deleteAuthor(authorId);
        return String.format(messageService.getMessage("message.author.delete"), authorId);
    }
}
