package otus.service.domenservice.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import otus.dao.CommentDao;
import otus.model.Book;
import otus.model.Comment;
import otus.service.domenservice.CommentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDAO;

    @Override
    public Comment saveComment(String name, Book book) {
        return commentDAO.save(new Comment(name, book));
    }

    @Override
    public Comment getComment(String id) {
        return commentDAO.findById(id).orElse(null);
    }

    @Override
    public List<Comment> getAllComment() {
        return commentDAO.findAll();
    }

    @Override
    public Comment updateComment(Comment comment) {
        return commentDAO.save(comment);
    }

    @Override
    public void deleteComment(Comment comment) {
        commentDAO.deleteById(comment.getId());
    }
}