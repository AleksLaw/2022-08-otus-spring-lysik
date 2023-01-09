package otus.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import otus.model.Book;

@RepositoryRestResource(path = "book2")
public interface BookDao extends JpaRepository<Book, Long> {

}
