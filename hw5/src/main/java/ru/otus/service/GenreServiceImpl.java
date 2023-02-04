package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.dao.GenreDAO;
import ru.otus.domain.Genre;
import ru.otus.exception.service.DeleteGenreException;
import ru.otus.exception.service.FindAllGenreException;
import ru.otus.exception.service.GetGenreByIdException;
import ru.otus.exception.service.SaveGenreException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreDAO genreDao;

    @Override
    public List<Genre> getAll() {
        try {
            return genreDao.getAll();
        } catch (Exception e) {
            throw new FindAllGenreException(e);
        }
    }

    @Override
    public Genre getGenreById(Long id) {
        try {
            return genreDao.getById(id);
        } catch (Exception e) {
            throw new GetGenreByIdException(id, e);
        }
    }

    @Override
    public Genre saveGenre(Genre genre) {
        try {
            return genreDao.save(genre);
        } catch (Exception e) {
            throw new SaveGenreException(genre, e);
        }
    }

    @Override
    public void deleteGenre(Long id) {
        try {
            genreDao.delete(id);
        } catch (Exception e) {
            throw new DeleteGenreException(id, e);
        }
    }
}
