package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.exception.DeleteBookException;
import ru.otus.exception.FindAllBookException;
import ru.otus.exception.GetBookByIdException;
import ru.otus.exception.SaveBookException;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.Genre;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.GenreRepository;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;


    @Override
    public Flux<Book> getAll() {
        return bookRepository.findAll().onErrorMap(FindAllBookException::new);
    }


    @Override
    public Mono<Book> getBookById(String id) {
        return bookRepository.findById(id).onErrorMap(error -> new GetBookByIdException(id, new Throwable("Not Found Id Book")));
    }


    @Override
    public Mono<Book> saveBook(Book book) {
        Mono<Author> authorMono = authorRepository.findById(book.getAuthor().getId());
        Mono<Genre> genreMono = genreRepository.findById(book.getGenre().getId());
        return Mono.zip(authorMono, genreMono)
                .flatMap(tuple -> bookRepository.save(Book.builder().author(tuple.getT1()).genre(tuple.getT2()).build()))
                .onErrorMap(error -> new SaveBookException(book, error));
    }


    @Override
    public Mono<Void> deleteBook(String id) {
        return bookRepository.deleteById(id).onErrorMap(error -> new DeleteBookException(id, error));
    }

}