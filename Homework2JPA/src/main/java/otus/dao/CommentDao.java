package otus.dao;


import otus.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentDao {
    Comment save(Comment genre);

    Comment update(Comment genre);

    Optional<Comment> getById(long id);

    List<Comment> getAllByBookId(long id);

    long deleteById(long id);
}
