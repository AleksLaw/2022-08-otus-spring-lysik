package otus.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import otus.dao.mongo.AuthorDao;
import otus.dao.mongo.BookDao;
import otus.dao.mongo.CommentDao;
import otus.dao.mongo.GenreDao;
import otus.model.mongo.Author;
import otus.model.mongo.Book;
import otus.model.mongo.Comment;
import otus.model.mongo.Genre;

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

    @ChangeSet(order = "001", id = "dropDb", author = "law", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertAuthors", author = "law")
    public void insertAuthors(AuthorDao repository) {
        author1 = repository.save(new Author("1", "Karl", "Marks"));
        author2 = repository.save(new Author("2", "Uzik", "Stalin"));
        author3 = repository.save(new Author("3", "Vova", "Lenin"));
        author4 = repository.save(new Author("4", "Fridrih", "Engels"));
    }

    @ChangeSet(order = "003", id = "insertGenres", author = "law")
    public void insertGenres(GenreDao repository) {
        genre1 = repository.save(new Genre("1", "Roman"));
        genre2 = repository.save(new Genre("2", "Poem"));
        genre3 = repository.save(new Genre("3", "Verse"));
        genre4 = repository.save(new Genre("4", "A note about your boy"));
    }

    @ChangeSet(order = "004", id = "insertComments", author = "law")
    public void insertComments(CommentDao repository) {
        comment1 = repository.save(new Comment("1", "so so book", null));
        comment2 = repository.save(new Comment("2", "maybe", null));
        comment3 = repository.save(new Comment("3", "terrible book", null));
        comment4 = repository.save(new Comment("4", "good book", null));
    }

    @ChangeSet(order = "005", id = "insertBooks", author = "law")
    public void insertBooks(BookDao repository) {
        book1 = repository.save(new Book("1", "Capital", author1, genre1, List.of(comment1, comment2)));
        book2 = repository.save(new Book("2", "Compositions", author2, genre2, List.of(comment3)));
        book3 = repository.save(new Book("3", "Imperialism as the highest stage of capitalism", author3, genre3, List.of(comment4)));
        book4 = repository.save(new Book("4", "Interrogation plan for a German spy", author4, genre4, List.of()));
    }

    @ChangeSet(order = "006", id = "insertCommentsw", author = "law")
    public void insertCommentsw(CommentDao repository) {
        comment1.setBook(book1);
        comment2.setBook(book1);
        comment3.setBook(book2);
        comment4.setBook(book3);
        repository.save(comment1);
        repository.save(comment2);
        repository.save(comment3);
        repository.save(comment4);
    }
}