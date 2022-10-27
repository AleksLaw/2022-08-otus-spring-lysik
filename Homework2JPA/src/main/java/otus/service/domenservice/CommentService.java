package otus.service.domenservice;


import otus.model.Comment;

import java.util.List;

public interface CommentService {
    Comment saveComment();

    Comment getCommentById();

    List<Comment> getAllCommentByBookId();

    Comment updateComment();

    long deleteComment();

}
