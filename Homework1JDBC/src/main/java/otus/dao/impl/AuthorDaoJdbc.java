package otus.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import otus.dao.AuthorDao;
import otus.dao.mapper.AuthorMapper;
import otus.model.Author;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.isNull;

@Repository
@RequiredArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public long count() {
        Long authorCount = namedParameterJdbcTemplate.getJdbcOperations()
                .queryForObject("select count(*) from authors", Long.class);
        return isNull(authorCount) ? 0 : authorCount;
    }

    @Override
    public long insert(Author author) {
        MapSqlParameterSource parSource = new MapSqlParameterSource();
        parSource.addValue("name", author.getName());
        parSource.addValue("surname", author.getSurname());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(
                "insert into authors (name, surname) values (:name, :surname)",
                parSource, keyHolder, new String[]{"id"});
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public long update(Author author) {
        return namedParameterJdbcTemplate.update(
                "update authors a set a.name=:name, a.surname=:surname where a.id=:id",
                Map.of("id", author.getId(), "name", author.getName(), "surname", author.getSurname())
        );
    }

    @Override
    public Author getById(long id) {
        return namedParameterJdbcTemplate.queryForObject(
                "select id, name, surname from authors where id=:id",
                Map.of("id", id), new AuthorMapper());
    }

    @Override
    public List<Author> getAll() {
        return namedParameterJdbcTemplate.getJdbcOperations()
                .query("select id, name, surname from authors", new AuthorMapper());
    }

    @Override
    public long deleteById(long id) {
        return namedParameterJdbcTemplate.update(
                "delete from authors where id=:id", Map.of("id", id));
    }
}
