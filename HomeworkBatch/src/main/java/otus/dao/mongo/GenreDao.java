package otus.dao.mongo;


import org.springframework.data.mongodb.repository.MongoRepository;
import otus.model.mongo.Genre;

public interface GenreDao extends MongoRepository<Genre, String> {
}
