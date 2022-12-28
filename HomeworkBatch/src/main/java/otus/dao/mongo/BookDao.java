package otus.dao.mongo;


import org.springframework.data.mongodb.repository.MongoRepository;
import otus.model.mongo.Book;

public interface BookDao extends MongoRepository<Book, String> {

}
