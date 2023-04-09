package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.entity.Genre;
import ru.otus.service.GenreService;
import ru.otus.service.MessageService;
import ru.otus.service.WrapperService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class ShellGenreController {

    private final GenreService genreService;
    private final WrapperService wrapperService;
    private final MessageService messageService;


    @ShellMethod(key = "genre.all")
    public List<Genre> getAll() {
        return genreService.getAll();
    }

    @ShellMethod(key = "genre.byId")
    public Genre getGenreById(@ShellOption Long genreId) {
        return genreService.getGenreById(genreId);
    }

    @ShellMethod(key = "genre.save")
    public Genre saveGenre() {
        return wrapperService.convertInput(
                Genre.class,
                "message.genre.write",
                genreService::saveGenre
        );
    }

    @ShellMethod(key = "genre.delete")
    public String deleteGenre(@ShellOption Long genreId) {
        genreService.deleteGenre(genreId);
        return String.format(messageService.getMessage("message.genre.delete"), genreId);
    }
}
