package ru.otus.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import ru.otus.dto.Book;
import ru.otus.dto.ErrorBookDto;
import ru.otus.feign.LibraryServiceClient;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final LibraryServiceClient feignClient;
    private final String diffPostfix;

    public BookServiceImpl(LibraryServiceClient feignClient, @Value("${diff-postfix}") String diffPostfix) {
        this.feignClient = feignClient;
        this.diffPostfix = diffPostfix;
    }

    @HystrixCommand(commandKey = "commonBookListKey", fallbackMethod = "sorryListOfBookFallback")
    @Override
    public List<Book> getAll() {
            return feignClient.bookList().stream().peek(bookDto -> {
                var title = bookDto.getName();
                bookDto.setName(title + diffPostfix);
            }).collect(Collectors.toList());
    }

    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    }, fallbackMethod = "sorryBookFallback")
    @Override
    public Book getBookById(Long id) {
        getDelayOnEvenId(id);
        Book book = feignClient.getBook(id);
        var title = book.getName();
        book.setName(title + diffPostfix);
        return book;
    }

    @HystrixCommand(commandKey = "commonBookListKey")
    @Override
    public Book saveBook(Book book) {
        Book bookDto = feignClient.saveBook(book);
        var title = book.getName();
        bookDto.setName(title + diffPostfix);
        bookDto.setName(book.getName().concat(diffPostfix));
        return bookDto;

    }

    @HystrixCommand(commandKey = "commonBookListKey")
    @Override
    public void deleteBook(Long id) {
        feignClient.deleteBook(id);
    }

    private List<Book> sorryListOfBookFallback() {
        return Collections.emptyList();
    }

    private Book sorryBookFallback(Long id) {
        return new ErrorBookDto("К сожалению, Вашу книгу с id " + id + "  пока не нашли");
    }

    @SneakyThrows
    private void getDelayOnEvenId(Long id) {
        if (id == null || id % 2 == 0) {
            Thread.sleep(2500);
        }
    }

}