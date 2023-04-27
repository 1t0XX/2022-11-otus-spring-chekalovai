package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.exception.DeleteAuthorException;
import ru.otus.exception.FindAllAuthorException;
import ru.otus.exception.GetAuthorByIdException;
import ru.otus.exception.SaveAuthorException;
import ru.otus.model.Author;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    private final BookRepository bookRepository;

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
    public Author getAuthorById(String id) {
        return authorRepository.findById(id).orElseThrow(() -> new GetAuthorByIdException(id, "Not Found Id Author"));
    }

    @Transactional
    @Override
    public Author saveAuthor(Author author) {
        try {
            if (author.getId() != null) {
                bookRepository.updateAllByAuthorId(author.getId(), author);
            }
            return authorRepository.save(author);
        } catch (Exception e) {
            throw new SaveAuthorException(author, e);
        }
    }

    @Transactional
    @Override
    public void deleteAuthor(String id) {
        try {
            authorRepository.deleteByIdCustom(id);
        } catch (Exception e) {
            throw new DeleteAuthorException(id, e);
        }
    }
}