package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.dao.AuthorDAO;
import ru.otus.domain.Author;
import ru.otus.exception.service.DeleteAuthorException;
import ru.otus.exception.service.FindAllAuthorException;
import ru.otus.exception.service.GetAuthorByIdException;
import ru.otus.exception.service.SaveAuthorException;

import java.util.List;
@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService{

    private final AuthorDAO authorDao;

    @Override
    public List<Author> getAll() {
        try {
            return authorDao.getAll();
        } catch (Exception e) {
            throw new FindAllAuthorException(e);
        }
    }

    @Override
    public Author getAuthorById(Long id) {
        try {
            return authorDao.getById(id);
        } catch (Exception e) {
            throw new GetAuthorByIdException(id, e);
        }
    }

    @Override
    public Author saveAuthor(Author author) {
        try {
            return authorDao.save(author);
        } catch (Exception e) {
            throw new SaveAuthorException(author, e);
        }
    }

    @Override
    public void deleteAuthor(Long id) {
        try {
            authorDao.delete(id);
        } catch (Exception e) {
            throw new DeleteAuthorException(id, e);
        }
    }
}
