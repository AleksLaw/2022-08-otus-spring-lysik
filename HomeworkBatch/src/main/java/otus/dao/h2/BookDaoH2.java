package otus.dao.h2;


import org.springframework.data.repository.CrudRepository;
import otus.model.mongo.Book;

public interface BookDaoH2 extends CrudRepository<Book, String> {

}
