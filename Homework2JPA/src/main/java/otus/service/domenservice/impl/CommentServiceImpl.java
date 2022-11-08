package otus.service.domenservice.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public Comment saveComment(String name, Book book) {
        return commentDAO.save(new Comment(name, book));
    }

    @Override
    public Comment getComment(long id) {
        return commentDAO.getById(id).orElse(null);
    }

    @Override
    public List<Comment> getAllComment() {
        return commentDAO.getAll();
    }

    @Override
    @Transactional
    public Comment updateComment(Comment comment) {
        return commentDAO.update(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Comment comment) {
        commentDAO.deleteById(comment.getId());
    }
}