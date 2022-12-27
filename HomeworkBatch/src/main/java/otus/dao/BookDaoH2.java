package otus.dao;


import org.springframework.data.repository.CrudRepository;
import otus.model.Book;

public interface BookDaoH2 extends CrudRepository<Book, String> {

}
