package otus.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import otus.dao.BookDao;
import otus.dao.mapper.BookMapper;
import otus.model.Book;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.isNull;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public long count() {
        Long bookCount = namedParameterJdbcTemplate.getJdbcOperations().queryForObject(
                "select count(*) from books", Long.class);
        return isNull(bookCount) ? 0 : bookCount;
    }

    @Override
    public long insert(Book book) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("name", book.getName());
        parameterSource.addValue("author_id", book.getAuthor().getId());
        parameterSource.addValue("genre_id", book.getGenre().getId());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(
                "insert into books (name, author_id, genre_id ) values (:name, :author_id, :genre_id )",
                parameterSource, keyHolder, new String[]{"id"});
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public long update(Book book) {
        return namedParameterJdbcTemplate.update(
                "update books b set b.name = :name, b.author =:author , b.genre= :genre where b.id =  :id",
                Map.of("id", book.getId(), "name", book.getName(), "author", book.getAuthor(), "genre", book.getGenre()));
    }

    @Override
    public Book getById(long id) {
        String sql = "select " +
                "b.id as b_id, b.name as b_name, a.id as a_id, a.name as a_name, a.surname, g.id as g_id, g.name as g_name  " +
                "from books b " +
                "left join authors a on b.author_id = a.id " +
                "left join genres g on b.genre_id = g.id " +
                "where b.id =  :id";
        List<Book> id1 = namedParameterJdbcTemplate.query(sql, Map.of("id", id), new BookMapper());
        return id1.get(0);
    }

    @Override
    public List<Book> getAll() {
        return namedParameterJdbcTemplate.getJdbcOperations().query(
                "select id, name, author, genre from books", new BookMapper());
    }

    @Override
    public int deleteById(long id) {
        return namedParameterJdbcTemplate.update(
                "delete from books where id =  :id", Map.of("id", id));
    }
}
