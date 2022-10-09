package otus.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import otus.dao.GenreDao;
import otus.dao.mapper.GenreMapper;
import otus.model.Genre;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.isNull;

@Repository
@RequiredArgsConstructor
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public long count() {
        Long genreCount = namedParameterJdbcTemplate.getJdbcOperations().queryForObject(
                "select count(*) from genres", Long.class);
        return isNull(genreCount) ? 0 : genreCount;
    }

    @Override
    public long insert(Genre genre) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("name", genre.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(
                "insert into genres (name) values (:name)", parameterSource, keyHolder, new String[]{"id"});
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public long update(Genre genre) {
        return namedParameterJdbcTemplate.update(
                "update genres g set g.name = :name where g.id = :id", Map.of("id", genre.getId(), "name", genre.getName()));
    }

    @Override
    public Genre getById(long id) {
        return namedParameterJdbcTemplate.queryForObject(
                "select id, name from genres where id =  :id", Map.of("id", id), new GenreMapper());
    }

    @Override
    public List<Genre> getAll() {
        return namedParameterJdbcTemplate.getJdbcOperations().query(
                "select id, name from genres", new GenreMapper());
    }

    @Override
    public int deleteById(long id) {
        return namedParameterJdbcTemplate.update(
                "delete from genres where id =  :id", Map.of("id", id));
    }
}
