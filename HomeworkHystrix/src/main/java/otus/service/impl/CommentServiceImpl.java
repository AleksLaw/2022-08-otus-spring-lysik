package otus.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.dao.CommentDao;
import otus.model.Book;
import otus.model.Comment;
import otus.service.BookService;
import otus.service.CommentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDAO;
    private final BookService bookService;

    @HystrixCommand(commandKey = "keyComments")
    @Override
    public Comment saveComment(Comment comment) {
        return commentDAO.save(comment);
    }

    @HystrixCommand(commandKey = "keyComments")
    @Override
    public Comment getComment(long id) {
        return commentDAO.findById(id).orElse(null);
    }

    @HystrixCommand(commandKey = "keyComments")
    @Override
    public List<Comment> getAllComment() {
        return commentDAO.findAll();
    }

    @HystrixCommand(commandKey = "keyComments")
    @Override
    public Comment updateComment(Comment comment) {
        return commentDAO.save(comment);
    }

    @HystrixCommand(commandKey = "keyComments")
    @Override
    public void deleteComment(Comment comment) {
        Comment comDb = getComment(comment.getId());
        Book book = bookService.getBook(comDb.getBook().getId());
        book.getComments().remove(comDb);
        bookService.saveBook(book);
    }
}