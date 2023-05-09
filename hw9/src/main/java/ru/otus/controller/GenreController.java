package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.otus.model.Genre;
import ru.otus.service.GenreService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping(path = "/genre")
    public String genreList(Model model) {
        model.addAttribute("genres", genreService.getAll());
        return "genre-list";
    }

    @GetMapping(path = {"/genre/{id}"})
    public String editGenrePage(@PathVariable("id") long genreId, @RequestParam("readOnly") boolean readOnly,
                                 Model model) {
        Genre genre = (genreId == 0) ? new Genre(null, "") : genreService.getGenreById(genreId);
        model.addAttribute("genre", genre);
        model.addAttribute("readOnly", readOnly);
        return "genre-edit";
    }

    @PostMapping(path = "/genre")
    public String saveGenre(@Valid Genre genre, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "genre-edit";
        }
        genreService.saveGenre(genre);
        return "redirect:/genre";
    }

    @DeleteMapping(path = "/genre/{id}")
    public String deleteGenre(@PathVariable("id") long genreId) {
        genreService.deleteGenre(genreId);
        return "redirect:/genre";
    }

}
