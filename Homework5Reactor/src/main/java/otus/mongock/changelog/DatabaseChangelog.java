package otus.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import otus.dao.AuthorDao;
import otus.dao.BookDao;
import otus.dao.CommentDao;
import otus.dao.GenreDao;
import otus.model.Author;
import otus.model.Book;
import otus.model.Comment;
import otus.model.Genre;

import java.util.List;

@ChangeLog
public class DatabaseChangelog {

    private Author author1;
    private Author author2;
    private Author author3;
    private Author author4;

    private Genre genre1;
    private Genre genre2;
    private Genre genre3;
    private Genre genre4;

    private Comment comment1;
    private Comment comment2;
    private Comment comment3;
    private Comment comment4;

    private Book book1;
    private Book book2;
    private Book book3;
    private Book book4;


    @ChangeSet(order = "001", id = "insertAuthors", author = "law")
    public void insertAuthors(AuthorDao repository) {
        author1 = repository.save(new Author("1", "Karl", "Marks")).block();
        author2 = repository.save(new Author("2", "Uzik", "Stalin")).block();
        author3 = repository.save(new Author("3", "Vova", "Lenin")).block();
        author4 = repository.save(new Author("4", "Fridrih", "Engels")).block();
    }

    @ChangeSet(order = "002", id = "insertGenres", author = "law")
    public void insertGenres(GenreDao repository) {
        genre1 = repository.save(new Genre("1", "Roman")).block();
        genre2 = repository.save(new Genre("2", "Poem")).block();
        genre3 = repository.save(new Genre("3", "Verse")).block();
        genre4 = repository.save(new Genre("4", "A note about your boy")).block();
    }

    @ChangeSet(order = "003", id = "insertComments", author = "law")
    public void insertComments(CommentDao repository) {
        comment1 = repository.save(new Comment("1", "so so book", null)).block();
        comment2 = repository.save(new Comment("2", "maybe", null)).block();
        comment3 = repository.save(new Comment("3", "terrible book", null)).block();
        comment4 = repository.save(new Comment("4", "good book", null)).block();
    }

    @ChangeSet(order = "004", id = "insertBooks", author = "law")
    public void insertBooks(BookDao repository) {
        book1 = repository.save(new Book("1", "Capital", author1, genre1, List.of(comment1, comment2))).block();
        book2 = repository.save(new Book("2", "Compositions", author2, genre2, List.of(comment3))).block();
        book3 = repository.save(new Book("3", "Imperialism as the highest stage of capitalism", author3, genre3, List.of(comment4))).block();
        book4 = repository.save(new Book("4", "Interrogation plan for a German spy", author4, genre4, List.of())).block();
    }

    @ChangeSet(order = "005", id = "insertComments2", author = "law")
    public void insertComments2(CommentDao repository) {
        repository.save(new Comment("1", "so so book", book1)).block();
        repository.save(new Comment("2", "maybe", book1)).block();
        repository.save(new Comment("3", "terrible book", book2)).block();
        repository.save(new Comment("4", "good book", book3)).block();
    }
}