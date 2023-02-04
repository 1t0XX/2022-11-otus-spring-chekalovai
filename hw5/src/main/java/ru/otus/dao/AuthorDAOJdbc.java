package ru.otus.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class AuthorDAOJdbc implements AuthorDAO {

    private final NamedParameterJdbcOperations jdbc;


    @Override
    public List<Author> getAll() {
        return jdbc.query("select id, name, surname from t_author order by id", new MapperAuthor());
    }

    @Override
    public Author getById(Long id) {
        return jdbc.queryForObject("select id, name, surname from t_author where id = :id", Map.of("id", id), new MapperAuthor());
    }

    @Override
    public Author save(Author author) {
        GeneratedKeyHolder kh = new GeneratedKeyHolder();
        MapSqlParameterSource params = convertAuthor2Map(author);
        return author.getId() == null || author.getId() <= 0 ? insertAuthor(author, params, kh) : updateAuthor(author, params);
    }

    private Author insertAuthor(Author author, MapSqlParameterSource params, GeneratedKeyHolder kh) {
        jdbc.update("insert into t_author(name, surname) values (:name, :surname)", params, kh);
        return new Author(kh.getKey().longValue(), author.getName(), author.getSurName());
    }

    private Author updateAuthor(Author author, MapSqlParameterSource params) {
        jdbc.update("update t_author set name = :name, surname = :lastname where id = :id", params);
        return author;
    }

    private MapSqlParameterSource convertAuthor2Map(Author author) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", author.getId());
        params.addValue("name", author.getName());
        params.addValue("surname", author.getSurName());
        return params;
    }

    @Override
    public void delete(Long id) {
        jdbc.update("delete from t_author where id = :id", Map.of("id", id));
    }

    private static class MapperAuthor implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            String surName = rs.getString("surname");
            return new Author(id, name, surName);
        }
    }
}
