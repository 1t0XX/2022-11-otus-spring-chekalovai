package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.model.Book;
import ru.otus.exception.*;
import ru.otus.repository.BookRepository;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;


    @Transactional(readOnly = true)
    @Override
    public List<Book> getAll() {
        try {
            return bookRepository.findAll();
        } catch (Exception e) {
            return Collections.emptyList();
            /*throw new FindAllBookException(e);*/
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new GetBookByIdException(id, new Throwable("Not Found Id Book")));
    }

    @Transactional
    @Override
    public Book saveBook(Book book) {
        try {
            return bookRepository.save(book);
        } catch (Exception e) {
            throw new SaveBookException(book, e);
        }
    }

    @Transactional
    @Override
    public void deleteBook(Long id) {
        try {
            bookRepository.deleteById(id);
        } catch (Exception e) {
            throw new DeleteBookException(id, e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getBookByAuthor(Long id) {
        try {
            return bookRepository.findAllByAuthorId(id);
        } catch (Exception e) {
            throw new GetBookByAuthorException(id, e);
        }
    }

}