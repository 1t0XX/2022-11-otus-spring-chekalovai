package ru.otus.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class GenreDAOJdbc implements GenreDAO {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public List<Genre> getAll() {
        return jdbc.query("select id, name from t_genre order by id", new MapperGenre());
    }

    @Override
    public Genre getById(Long id) {
        return jdbc.queryForObject("select id, name from t_genre where id = :id", Map.of("id", id), new MapperGenre());
    }

    @Override
    public Genre save(Genre genre) {
        GeneratedKeyHolder kh = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", genre.getId());
        params.addValue("name", genre.getName());
        return genre.getId() == null || genre.getId() <= 0 ? insertGenre(genre, params, kh) : updateGenre(genre, params);
    }

    private Genre insertGenre(Genre genre, MapSqlParameterSource params, GeneratedKeyHolder kh) {
        jdbc.update("insert into t_genre(name) values (:name)", params, kh);
        return new Genre(kh.getKey().longValue(), genre.getName());
    }

    private Genre updateGenre(Genre genre, MapSqlParameterSource params) {
        jdbc.update("update t_genre set name = :name where id = :id", params);
        return genre;
    }

    @Override
    public void delete(Long id) {
        jdbc.update("delete from t_genre where id = :id", Map.of("id", id));
    }

    private static class MapperGenre implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            return new Genre(id, name);
        }
    }
}
