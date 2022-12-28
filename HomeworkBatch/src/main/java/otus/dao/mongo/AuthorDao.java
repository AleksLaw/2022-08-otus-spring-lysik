package otus.dao.mongo;


import org.springframework.data.mongodb.repository.MongoRepository;
import otus.model.mongo.Author;

public interface AuthorDao extends MongoRepository<Author, String> {

}
