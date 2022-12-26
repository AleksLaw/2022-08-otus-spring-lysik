package otus.service;


import otus.model.Comment;

import java.util.List;

public interface CommentService {

    Comment saveComment(Comment comment);

    Comment getComment(long id);

    List<Comment> getAllComment();

    Comment updateComment(Comment comment);

    void deleteComment(Comment comment);

}
