package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.exception.DeleteAuthorException;
import ru.otus.exception.FindAllAuthorException;
import ru.otus.exception.GetAuthorByIdException;
import ru.otus.exception.SaveAuthorException;
import ru.otus.model.Author;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;


@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;


    @Override
    public Flux<Author> getAll() {
        return authorRepository.findAll().onErrorMap(FindAllAuthorException::new);
    }


    @Override
    public Mono<Author> getAuthorById(String id) {
        return authorRepository.findById(id).onErrorMap(error -> new GetAuthorByIdException(id, new Throwable("Not Found Id Author")));
    }


    @Override
    public Mono<Author> saveAuthor(Author author) {
        if (author.getId().equals("")) {
            author.setId(null);
        } else {
            Flux.from(bookRepository.findBooksByAuthorId(author.getId()).flatMap(book -> {
                book.setAuthor(author);
                return bookRepository.save(book);
            })).subscribe();
        }

        return authorRepository.save(author).onErrorMap(error -> new SaveAuthorException(author, error));
    }


    @Override
    public Mono<Void> deleteAuthor(String id) {
        return authorRepository.deleteById(id).and(bookRepository.deleteBooksByAuthorId(id)).onErrorMap(error -> new DeleteAuthorException(id, error));
    }
}