package otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import otus.model.Author;
import otus.model.Book;
import otus.model.Comment;
import otus.model.Genre;
import otus.service.domenservice.AuthorService;
import otus.service.domenservice.BookService;
import otus.service.domenservice.GenreService;
import otus.service.ioservice.IOService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommandBook {

    public static final String NOT_FIND = "____________________ Не найдена ____________________";
    public static final String INPUT_ID_BOOK = "Введите id книги";
    public static final String INPUT_ID_AUTHOR = "Введите id автора";
    public static final String INPUT_ID_GENRE = "Введите id жанра";
    public static final String DELETED = " удалена";
    public static final String UPDATED = " обновлена";
    public static final String ADDED = " добавлена";
    public static final String INPUT_BOOK_NAME = "Введите название книги";
    public static final String NOT_DELETED = "Книга не удалена";

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final IOService ioService;

    @ShellMethod(value = "Сохранение книги", key = {"sb", "SaveBook"})
    public void saveBook() {
        Book book = new Book();
        book.setName(ioService.getString(INPUT_BOOK_NAME));
        fillBookField(book);
        ioService.outputString(bookService.saveBook(book) + ADDED);
    }

    @ShellMethod(value = "Получение комментариев книги", key = {"cb", "GetComment"})
    public void getComments() {
        Book book = bookService.getBook(ioService.getString(INPUT_ID_BOOK));
        if (book == null) {
            ioService.outputString(NOT_FIND);
            bookService.getAllBook().forEach(s -> ioService.outputString(s.toString()));
        } else {
            List<Comment> comments = book.getComments();
            comments.forEach(c -> ioService.outputString(c.toString()));
        }
    }

    @ShellMethod(value = "Получение всех книг", key = {"ab", "AllBooks"})
    public void getAllBook() {
        bookService.getAllBook().forEach(book -> ioService.outputString(book.toString()));
    }

    @ShellMethod(value = "Получение книги", key = {"gb", "GetBook"})
    public void getBooks() {
        Book book = bookService.getBook(ioService.getString(INPUT_ID_BOOK));
        if (book == null) {
            ioService.outputString(NOT_FIND);
            bookService.getAllBook().forEach(s -> ioService.outputString(s.toString()));
        } else {
            ioService.outputString(book.toString());
        }
    }

    @ShellMethod(value = "Обновление книги", key = {"ub", "UpdateBook"})
    public void updateBook() {
        Book book = bookService.getBook(ioService.getString(INPUT_ID_BOOK));
        if (book == null) {
            ioService.outputString(NOT_FIND);
            getAllBook();
            return;
        } else {
            book.setName(ioService.getString(INPUT_BOOK_NAME));
            fillBookField(book);
        }
        ioService.outputString(bookService.updateBook(book) + UPDATED);
    }

    @ShellMethod(value = "Удаление книги", key = {"db", "DeleteBook"})
    public void deleteBook() {
        Book book = bookService.getBook(ioService.getString(INPUT_ID_BOOK));
        if (book == null) {
            ioService.outputString(NOT_FIND);
            bookService.getAllBook().forEach(s -> ioService.outputString(s.toString()));
        } else {
            try {
                bookService.deleteBook(book);
                ioService.outputString(book + DELETED);
            } catch (Exception e) {
                ioService.outputString(book + NOT_DELETED);
            }
        }
    }

    private void fillBookField(Book book) {
        Author author = authorService.getAuthor(ioService.getString(INPUT_ID_AUTHOR));
        if (author == null) {
            ioService.outputString(NOT_FIND);
            authorService.getAllAuthor().forEach(s -> ioService.outputString(s.toString()));
            fillBookField(book);
        } else {
            book.setAuthor(author);
            setGenre(book);
        }
    }

    private void setGenre(Book book) {
        Genre genre = genreService.getGenre(ioService.getString(INPUT_ID_GENRE));
        if (genre == null) {
            ioService.outputString(NOT_FIND);
            genreService.getAllGenre().forEach(s -> ioService.outputString(s.toString()));
            setGenre(book);
        } else {
            book.setGenre(genre);
        }
    }
}
