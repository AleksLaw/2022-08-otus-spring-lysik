package otus.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import otus.dao.AuthorDao;
import otus.model.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorDaoJpa implements AuthorDao {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public Author save(Author author) {
        if (author.getId() == null) {
            em.persist(author);
        } else {
            em.merge(author);
        }
        return author;
    }

    @Override
    public Author update(Author author) {
        return em.merge(author);
    }

    @Override
    public Optional<Author> getById(long id) {
        return Optional.ofNullable(
                em.find(Author.class, id)
        );
    }

    @Override
    public List<Author> getAll() {
        return em.createQuery("SELECT a FROM Author a", Author.class)
                .getResultList();
    }

    @Override
    public void deleteById(long id) {
        getById(id).ifPresent(em::remove);
    }
}
