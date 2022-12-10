package otus.dao;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import otus.model.Book;

public interface BookDao extends ReactiveMongoRepository<Book, String> {
}
