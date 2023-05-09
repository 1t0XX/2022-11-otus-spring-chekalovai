package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.otus.model.Book;
import ru.otus.service.BookService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping(path = "/book")
    public String bookList(Model model) {
        model.addAttribute("books", bookService.getAll());
        return "book-list";
    }

    @GetMapping(path = {"/book/{id}"})
    public String editBookPage(@PathVariable("id") long bookId, @RequestParam("readOnly") boolean readOnly,
                               Model model) {
        Book book = (bookId == 0) ? new Book(null, "", null, null) : bookService.getBookById(bookId);
        model.addAttribute("book", book);
        model.addAttribute("readOnly", readOnly);
        return "book-edit";
    }

    @PostMapping(path = "/book")
    public String saveBook(@Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "book-edit";
        }
        bookService.saveBook(book);
        return "redirect:/book";
    }

    @DeleteMapping(path = "/book/{id}")
    public String deleteBook(@PathVariable("id") long bookId) {
        bookService.deleteBook(bookId);
        return "redirect:/book";
    }

}
