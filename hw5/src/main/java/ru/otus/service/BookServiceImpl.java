package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.dao.BookDAO;
import ru.otus.domain.Book;
import ru.otus.exception.service.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDAO bookDao;

    @Override
    public List<Book> getAll() {
        try {
            return bookDao.getAll();
        } catch (Exception e) {
            throw new FindAllBookException(e);
        }
    }

    @Override
    public Book getBookById(Long id) {
        try {
            return bookDao.getById(id);
        } catch (Exception e) {
            throw new GetBookByIdException(id, e);
        }
    }

    @Override
    public Book saveBook(Book book) {
        try {
            return bookDao.save(book);
        } catch (Exception e) {
            throw new SaveBookException(book, e);
        }
    }

    @Override
    public void deleteBook(Long id) {
        try {
            bookDao.delete(id);
        } catch (Exception e) {
            throw new DeleteBookException(id, e);
        }
    }

    @Override
    public List<Book> getBookByAuthor(Long id) {
        try {
            return bookDao.getByAuthorId(id);
        } catch (Exception e) {
            throw new GetBookByAuthorException(id, e);
        }
    }
}
