package otus.dao;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import otus.model.Author;

public interface AuthorDao extends ReactiveMongoRepository<Author, String> {
}
