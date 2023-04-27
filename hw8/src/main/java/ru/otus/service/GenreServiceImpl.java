package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.exception.DeleteGenreException;
import ru.otus.exception.FindAllGenreException;
import ru.otus.exception.GetGenreByIdException;
import ru.otus.exception.SaveGenreException;
import ru.otus.model.Genre;
import ru.otus.repository.BookRepository;
import ru.otus.repository.GenreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Genre> getAll() {
        try {
            return genreRepository.findAll();
        } catch (Exception e) {
            throw new FindAllGenreException(e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Genre getGenreById(String id) {
        return genreRepository.findById(id).orElseThrow(() -> new GetGenreByIdException(id, "Not Found Id Genre"));
    }

    @Transactional
    @Override
    public Genre saveGenre(Genre genre) {
        try {
            if (genre.getId() != null) {
                bookRepository.updateAllByGenreId(genre.getId(), genre);
            }
            return genreRepository.save(genre);
        } catch (Exception e) {
            throw new SaveGenreException(genre, e);
        }
    }

    @Transactional
    @Override
    public void deleteGenre(String id) {
        try {
            genreRepository.deleteByIdCustom(id);
        } catch (Exception e) {
            throw new DeleteGenreException(id, e);
        }
    }
}