package otus.service;

import org.springframework.stereotype.Service;
import otus.model.h2.AuthorH2;
import otus.model.h2.BookH2;
import otus.model.h2.CommentH2;
import otus.model.h2.GenreH2;
import otus.model.mongo.Author;
import otus.model.mongo.Book;
import otus.model.mongo.Comment;
import otus.model.mongo.Genre;

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
