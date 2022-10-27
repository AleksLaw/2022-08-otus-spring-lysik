package otus.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import otus.dao.CommentDao;
import otus.model.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

@Repository
@RequiredArgsConstructor
public class CommentDaoJpa implements CommentDao {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == null) {
            em.persist(comment);
        } else {
            em.merge(comment);
        }
        return comment;
    }

    @Override
    public Comment update(Comment comment) {
        return em.merge(comment);
    }

    @Override
    public Optional<Comment> getById(long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public List<Comment> getAllByBookId(long id) {
        return em.createQuery("SELECT c FROM Comment c where c.book.id=:id", Comment.class)
                .setParameter("id", id)
                .setHint(FETCH.getKey(), em.getEntityGraph("commentGraph"))
                .getResultList();
    }

    @Override
    public long deleteById(long id) {
        return em.createQuery("delete from Comment c where c.id=:id ")
                .setParameter("id", id)
                .executeUpdate();
    }
}
