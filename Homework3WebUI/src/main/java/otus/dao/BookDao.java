package otus.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import otus.model.Book;

public interface BookDao extends JpaRepository<Book, Long> {

}
