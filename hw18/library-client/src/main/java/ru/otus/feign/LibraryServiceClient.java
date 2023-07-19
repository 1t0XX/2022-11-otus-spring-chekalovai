package ru.otus.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.otus.dto.Book;

import java.util.List;

@FeignClient(name = "library-service")
public interface LibraryServiceClient {

    @GetMapping(path = "/api/book")
    List<Book> bookList();

    @GetMapping(path = {"/api/book/{id}"})
    Book getBook(@PathVariable("id") long bookId);

    @PostMapping(path = "/api/book")
    Book saveBook(@RequestBody Book book);

    @DeleteMapping(path = "/api/book/{id}")
    void deleteBook(@PathVariable("id") long bookId);

}
