package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.entity.Genre;
import ru.otus.exception.service.DeleteGenreException;
import ru.otus.exception.service.FindAllGenreException;
import ru.otus.exception.service.GetGenreByIdException;
import ru.otus.exception.service.SaveGenreException;
import ru.otus.repository.GenreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

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
    public Genre getGenreById(Long id) {
        return genreRepository.findById(id).orElseThrow(() -> new GetGenreByIdException(id, "Not Found Id Genre"));
    }

    @Transactional
    @Override
    public Genre saveGenre(Genre genre) {
        try {
            return genreRepository.save(genre);
        } catch (Exception e) {
            throw new SaveGenreException(genre, e);
        }
    }

    @Transactional
    @Override
    public void deleteGenre(Long id) {
        try {
            genreRepository.deleteById(id);
        } catch (Exception e) {
            throw new DeleteGenreException(id, e);
        }
    }
}