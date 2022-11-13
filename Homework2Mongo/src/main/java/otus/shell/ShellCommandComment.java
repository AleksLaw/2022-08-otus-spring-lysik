package otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import otus.model.Book;
import otus.model.Comment;
import otus.service.domenservice.BookService;
import otus.service.domenservice.CommentService;
import otus.service.ioservice.IOService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommandComment {

    public static final String NOT_FIND = "____________________ Не найден ____________________";
    public static final String INPUT_ID = "Введите id комментария";
    public static final String INPUT_ID_BOOK = "Введите id книги";
    public static final String ADDED = " добавлен";
    public static final String INPUT_TEXT = "Введите текст комментария";
    public static final String UPDATED = " обновлен";
    public static final String DELETED = " удален";
    public static final String NOT_DELETED = "Комментарий не удален";

    private final CommentService commentService;
    private final BookService bookService;
    private final IOService ioService;

    @ShellMethod(value = "Сохранение коментария", key = {"sc", "SaveComment"})
    public void saveComment() {
        Book book = bookService.getBook(ioService.getString(INPUT_ID_BOOK));
        if (book == null) {
            ioService.outputString(NOT_FIND);
            bookService.getAllBook().forEach(s -> ioService.outputString(s.toString()));
        } else {
            String name = ioService.getString(INPUT_TEXT);
            Comment comment = commentService.saveComment(name, book);
            List<Comment> comments = book.getComments();
            comments.add(comment);
            book.setComments(comments);
            bookService.saveBook(book);
            ioService.outputString(comment + ADDED);
        }
    }

    @ShellMethod(value = "Получение всех коментариев", key = {"ac", "AllComments"})
    public void getAllComments() {
        commentService.getAllComment().forEach(comment -> ioService.outputString(comment.toString()));
    }

    @ShellMethod(value = "Получение коментария", key = {"gc", "GetComment"})
    public void getComment() {
        Comment comment = commentService.getComment(ioService.getString(INPUT_ID));
        if (comment == null) {
            ioService.outputString(NOT_FIND);
            getAllComments();
        } else {
            ioService.outputString(comment.toString());
        }
    }

    @ShellMethod(value = "Обновление коментария", key = {"uc", "UpdateComment"})
    public void updateComment() {
        Comment comment = commentService.getComment(ioService.getString(INPUT_ID));
        if (comment == null) {
            ioService.outputString(NOT_FIND);
            getAllComments();
        } else {
            comment.setText(ioService.getString(INPUT_TEXT));
            Book book = bookService.getBook(ioService.getString(INPUT_ID_BOOK));
            if (book == null) {
                bookService.getAllBook().forEach(b -> ioService.outputString(b.toString()));
            } else {
                ioService.outputString(commentService.updateComment(comment) + UPDATED);
            }
        }
    }

    @ShellMethod(value = "Удаление коментария", key = {"dc", "DeleteComment"})
    public void deleteComment() {
        Comment comment = commentService.getComment(ioService.getString(INPUT_ID));
        if (comment == null) {
            ioService.outputString(NOT_FIND);
            getAllComments();
        } else {
            try {
                commentService.deleteComment(comment);
                ioService.outputString(comment + DELETED);
            } catch (Exception e) {
                ioService.outputString(comment + NOT_DELETED);
            }
        }
    }
}
