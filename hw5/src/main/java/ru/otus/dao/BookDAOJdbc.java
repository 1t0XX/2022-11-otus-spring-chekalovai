package ru.otus.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.exception.dao.BookAuthorNotSetException;
import ru.otus.exception.dao.BookGenreNotSetException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class BookDAOJdbc implements BookDAO {

    private final NamedParameterJdbcOperations jdbc;

    private final AuthorDAO authorDao;

    @Override
    public List<Book> getAll() {
        return jdbc.query(
                "select b.id, b.name, b.author_id, b.genre_id, a.name author_name, a.surname author_surname, g.name genre_name " +
                        "from t_book b " +
                        "INNER JOIN t_author a ON a.id = b.author_id " +
                        "INNER JOIN t_genre g ON g.id = b.genre_id",
                new MapperBook()
        );
    }

    @Override
    public Book getById(Long id) {
        return jdbc.queryForObject(
                "select b.id, b.name, b.author_id, b.genre_id, a.name author_name, a.surname author_surname, g.name genre_name " +
                        "from t_book b " +
                        "INNER JOIN t_author a ON a.id = b.author_id " +
                        "INNER JOIN t_genre g ON g.id = b.genre_id " +
                        "where b.id = :id",
                Map.of("id", id),
                new MapperBook()
        );
    }

    @Override
    public Book save(Book book) {
        GeneratedKeyHolder kh = new GeneratedKeyHolder();
        MapSqlParameterSource params = convertBook2Map(book);
        return book.getId() == null || book.getId() <= 0 ? insertBook(book, params, kh) : updateBook(book, params);
    }

    @Override
    public List<Book> getByAuthorId(Long id) {
        Author author = authorDao.getById(id);
        return jdbc.query(
                "select b.id, b.name, g.id genre_id, g.name genre_name " +
                        "from t_book b " +
                        "INNER JOIN t_genre g ON g.id = b.genre_id " +
                        "where b.author_id = :id",
                Map.of("id", id),
                new MapperBookByAuthor(author)
        );
    }

    @Override
    public void delete(Long id) {
        jdbc.update("delete from t_book where id = :id", Map.of("id", id));
    }

    private Book insertBook(Book book, MapSqlParameterSource params, GeneratedKeyHolder kh) {
        jdbc.update("insert into t_book(name, author_id, genre_id) values (:name, :author_id, :genre_id)", params, kh);
        return new Book(kh.getKey().longValue(), book.getName(), book.getAuthor(), book.getGenre());
    }

    private Book updateBook(Book book, MapSqlParameterSource params) {
        jdbc.update("update t_book set name = :name, author_id = :author_id, genre_id = :genre_id where id = :id", params);
        return book;
    }


    private MapSqlParameterSource convertBook2Map(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", book.getId());
        params.addValue("name", book.getName());
        if (book.getAuthor() == null || book.getAuthor().getId() == null) {
            throw new BookAuthorNotSetException();
        }
        params.addValue("author_id", book.getAuthor().getId());
        if (book.getGenre() == null || book.getGenre().getId() == null) {
            throw new BookGenreNotSetException();
        }
        params.addValue("genre_id", book.getGenre().getId());
        return params;
    }


    private static class MapperBook implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            long authorId = rs.getLong("author_id");
            long genreId = rs.getLong("genre_id");
            String authorName = rs.getString("author_name");
            String authorSurName = rs.getString("author_surname");
            String genreName = rs.getString("genre_name");
            return new Book(id, name, new Author(authorId, authorName, authorSurName), new Genre(genreId, genreName));
        }
    }

    @RequiredArgsConstructor
    private static class MapperBookByAuthor implements RowMapper<Book> {
        private final Author author;

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            Long genreId = rs.getLong("genre_id");
            String genreName = rs.getString("genre_name");
            return new Book(id, name, author, new Genre(genreId, genreName));
        }
    }
}
