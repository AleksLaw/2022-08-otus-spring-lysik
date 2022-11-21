package otus.dao;


import org.springframework.data.mongodb.repository.MongoRepository;
import otus.model.Genre;

public interface GenreDao extends MongoRepository<Genre, String> {

}
