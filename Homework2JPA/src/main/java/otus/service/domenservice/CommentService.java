package otus.service.domenservice;


import otus.model.Book;
import otus.model.Comment;

import java.util.List;

public interface CommentService {

    Comment saveComment(String name, Book book);

    Comment getComment(long id);

    List<Comment> getAllComment();

    Comment updateComment(Comment comment);

    void deleteComment(Comment comment);

}
