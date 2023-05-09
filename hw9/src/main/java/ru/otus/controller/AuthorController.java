package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.otus.model.Author;
import ru.otus.service.AuthorService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping(path = "/author")
    public String authorList(Model model) {
        model.addAttribute("authors", authorService.getAll());
        return "author-list";
    }

    @GetMapping(path = {"/author/{id}"})
    public String editAuthorPage(@PathVariable("id") long authorId, @RequestParam("readOnly") boolean readOnly,
                               Model model) {
        Author author = (authorId == 0) ? new Author(null, "", "") : authorService.getAuthorById(authorId);
        model.addAttribute("author", author);
        model.addAttribute("readOnly", readOnly);
        return "author-edit";
    }

    @PostMapping(path = "/author")
    public String saveAuthor(@Valid Author author, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "author-edit";
        }
        authorService.saveAuthor(author);
        return "redirect:/author";
    }

    @DeleteMapping(path = "/author/{id}")
    public String deleteAuthor(@PathVariable("id") long authorId) {
        authorService.deleteAuthor(authorId);
        return "redirect:/author";
    }

}
