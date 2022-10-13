package otus.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import otus.model.Author;
import otus.model.Book;
import otus.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Book(rs.getLong("b_id"), rs.getString("b_name"),
                new Author(rs.getLong("a_id"), rs.getString("a_name"), rs.getString("surname")),
                new Genre(rs.getLong("g_id"), rs.getString("g_name"))
        );
    }
}
