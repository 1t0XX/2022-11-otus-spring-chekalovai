package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.entity.Author;
import ru.otus.exception.service.DeleteAuthorException;
import ru.otus.exception.service.FindAllAuthorException;
import ru.otus.exception.service.GetAuthorByIdException;
import ru.otus.exception.service.SaveAuthorException;
import ru.otus.repository.AuthorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Author> getAll() {
        try {
            return authorRepository.findAll();
        } catch (Exception e) {
            throw new FindAllAuthorException(e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Author getAuthorById(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new GetAuthorByIdException(id, "Not Found Id Author"));
    }

    @Transactional
    @Override
    public Author saveAuthor(Author author) {
        try {
            return authorRepository.save(author);
        } catch (Exception e) {
            throw new SaveAuthorException(author, e);
        }
    }

    @Transactional
    @Override
    public void deleteAuthor(Long id) {
        try {
            authorRepository.deleteById(id);
        } catch (Exception e) {
            throw new DeleteAuthorException(id, e);
        }
    }
}