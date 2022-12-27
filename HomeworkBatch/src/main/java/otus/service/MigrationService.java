package otus.service;

import org.springframework.stereotype.Service;
import otus.model.*;

@Service
public class MigrationService {
    public GenreH2 genreProcessor(Genre genre) {
        return new GenreH2(
                Long.parseLong(genre.getId()),
                genre.getName()
        );
    }

    public AuthorH2 authorProcessor(Author author) {
        return new AuthorH2(
                Long.parseLong(author.getId()),
                author.getName(),
                author.getSurname()
        );
    }

    public CommentH2 commentProcessor(Comment comment) {
        return new CommentH2(
                Long.parseLong(comment.getId()),
                comment.getText(),
                Long.parseLong(comment.getBook().getId())
        );
    }

    public BookH2 bookProcessor(Book book) {
        return new BookH2(
                Long.parseLong(book.getId()),
                book.getName(),
                Long.parseLong(book.getAuthor().getId()),
                Long.parseLong(book.getGenre().getId())
        );
    }
}
