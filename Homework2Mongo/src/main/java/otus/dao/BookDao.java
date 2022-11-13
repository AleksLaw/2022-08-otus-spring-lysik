package otus.dao;


import org.springframework.data.mongodb.repository.MongoRepository;
import otus.model.Book;

public interface BookDao extends MongoRepository<Book, String> {

}
