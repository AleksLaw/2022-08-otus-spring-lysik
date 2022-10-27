package otus.service.domenservice.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import otus.dao.CommentDao;
import otus.model.Book;
import otus.model.Comment;
import otus.service.domenservice.BookService;
import otus.service.domenservice.CommentService;
import otus.service.ioservice.IOService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    public static final String INPUT_ID_COMMENT = "Введите id комментария";
    public static final String INPUT_ID_BOOK = "Введите id книги";
    public static final String INPUT_TEXT = "Введите текст комментария";
    public static final String COMMENT_WITH_ID = "Комментарий с ид = ";
    public static final String DELETED = " удален";
    public static final String SAVE_COMMENT = "Сохранение комментария";
    public static final String NOT_DELETED = "Автор не может быть удален. Он является автором книги которая у нас есть";
    public static final String NOT_FIND = "Не найден";
    public static final String COMMENT_FOR_BOOK_ID = "Комментарии к книге id ";
    private final CommentDao commentDAO;
    private final IOService ioService;
    private final BookService bookService;


    @Override
    @Transactional
    public Comment saveComment() {
        ioService.outputString(SAVE_COMMENT);
        Comment comment = commentDAO.save(createComment());
        ioService.outputString(comment.toString());
        return comment;
    }

    @Override
    @Transactional(readOnly = true)
    public Comment getCommentById() {
        Comment comment = commentDAO.getById(getId(INPUT_ID_COMMENT)).orElse(null);
        if (comment == null) {
            ioService.outputString(NOT_FIND);
            getAllCommentByBookId();
        } else {
            ioService.outputString(comment.toString());
        }
        return comment;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getAllCommentByBookId() {
        long id = getId(INPUT_ID_BOOK);
        List<Comment> allComment = commentDAO.getAllByBookId(id);
        ioService.outputString(COMMENT_FOR_BOOK_ID + id);
        allComment.forEach(comment -> ioService.outputString(comment.toString()));
        return allComment;
    }

    @Override
    @Transactional
    public Comment updateComment() {
        long id = checkComment(INPUT_ID_COMMENT).getId();
        Comment comment = createComment();
        comment.setId(id);
        Comment update = commentDAO.update(comment);
        ioService.outputString(update.toString());
        return update;
    }

    @Override
    @Transactional
    public long deleteComment() {
        long id = checkComment(INPUT_ID_COMMENT).getId();
        long rez = 0;
        try {
            rez = commentDAO.deleteById(id);
            ioService.outputString(COMMENT_WITH_ID + id + DELETED);
        } catch (RuntimeException e) {
            ioService.outputString(NOT_DELETED);
        }
        return rez;
    }

    private Comment createComment() {
        ioService.outputString(INPUT_TEXT);
        String name = ioService.getString();
        Book book = bookService.getBook();
        return new Comment(name, book);
    }

    private long getId(String s) {
        ioService.outputString(s);
        return ioService.getLong();
    }

    private Comment checkComment(String s) {
        Comment comment;
        do {
            long id = getId(s);
            comment = commentDAO.getById(id).orElse(null);
            if (comment == null) {
                ioService.outputString(NOT_FIND);
                getAllCommentByBookId();
            }
        } while (comment == null);
        return comment;
    }
}