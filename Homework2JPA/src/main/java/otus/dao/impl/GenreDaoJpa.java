package otus.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import otus.dao.GenreDao;
import otus.model.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GenreDaoJpa implements GenreDao {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public Genre save(Genre genre) {
        if (genre.getId() == null) {
            em.persist(genre);
        } else {
            em.merge(genre);
        }
        return genre;
    }

    @Override
    public Genre update(Genre genre) {
        return em.merge(genre);
    }

    @Override
    public Optional<Genre> getById(long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    public List<Genre> getAll() {
        return em.createQuery("SELECT a FROM Genre a", Genre.class)
                .getResultList();
    }

    @Override
    public long deleteById(long id) {
        return em.createQuery("delete from Genre g where g.id=:id")
                .setParameter("id", id)
                .executeUpdate();
    }
}
