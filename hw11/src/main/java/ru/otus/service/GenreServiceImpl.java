package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.exception.DeleteGenreException;
import ru.otus.exception.FindAllGenreException;
import ru.otus.exception.GetGenreByIdException;
import ru.otus.exception.SaveGenreException;
import ru.otus.model.Genre;
import ru.otus.repository.BookRepository;
import ru.otus.repository.GenreRepository;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;


    @Override
    public Flux<Genre> getAll() {
        return genreRepository.findAll().onErrorMap(FindAllGenreException::new);
    }


    @Override
    public Mono<Genre> getGenreById(String id) {
        return genreRepository.findById(id).onErrorMap(error -> new GetGenreByIdException(id, new Throwable("Not Found Id Genre")));
    }


    @Override
    public Mono<Void> saveGenre(Genre genre) {
        if (genre.getId().equals("")) {
            genre.setId(UUID.randomUUID().toString());
        }
        return genreRepository.save(genre).then(bookRepository.updateAllByGenreId(genre.getId(), genre)).onErrorMap(error -> new SaveGenreException(genre, error));

    }


    @Override
    public Mono<Void> deleteGenre(String id) {
           return genreRepository.deleteById(id).and(bookRepository.deleteBooksByGenreId(id)).onErrorMap(error -> new DeleteGenreException(id, error));
    }
}