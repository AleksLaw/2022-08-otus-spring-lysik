package otus.dao;


import org.springframework.data.mongodb.repository.MongoRepository;
import otus.model.Author;

public interface AuthorDao extends MongoRepository<Author, String> {

}
