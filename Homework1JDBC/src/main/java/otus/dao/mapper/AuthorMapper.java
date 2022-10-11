package otus.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import otus.model.Author;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorMapper implements RowMapper<Author> {

    @Override
    public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Author(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("surname")
        );
    }
}
