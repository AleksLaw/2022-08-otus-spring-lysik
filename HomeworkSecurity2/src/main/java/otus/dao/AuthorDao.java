package otus.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import otus.model.Author;

public interface AuthorDao extends JpaRepository<Author, Long> {

}
