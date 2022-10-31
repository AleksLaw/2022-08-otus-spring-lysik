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
        try {
            em.getEntityManagerFactory().addNamedEntityGraph(FETCH.getKey(), em.getEntityGraph("comment-graph"));
            return Optional.ofNullable(em.find(Comment.class, id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Comment> getAll() {
        return em.createQuery("SELECT c FROM Comment c", Comment.class)
                .setHint(FETCH.getKey(), em.getEntityGraph("comment-graph"))
                .getResultList();
    }

    @Override
    public void deleteById(long id) {
        getById(id).ifPresent(em::remove);
    }
}
