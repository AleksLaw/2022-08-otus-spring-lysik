package otus.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import otus.dao.BookDao;
import otus.model.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

@Repository
@RequiredArgsConstructor
public class BookDaoJpa implements BookDao {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            em.persist(book);
        } else {
            em.merge(book);
        }
        return book;
    }

    @Override
    public Book update(Book book) {
        return em.merge(book);
    }

    @Override
    public Optional<Book> getById(long id) {
        return Optional.ofNullable(
                em.find(Book.class, id)
        );
    }

    @Override
    public List<Book> getAll() {
        return em.createQuery("SELECT b FROM Book b", Book.class)
                .setHint(FETCH.getKey(), em.getEntityGraph("book-graph"))
                .getResultList();
    }

    @Override
    public long deleteById(long id) {
        return em.createQuery("delete from Book b where b.id=:id ")
                .setParameter("id", id)
                .executeUpdate();
    }
}
