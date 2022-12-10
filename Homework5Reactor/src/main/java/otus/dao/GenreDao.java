package otus.dao;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import otus.model.Genre;

public interface GenreDao extends ReactiveMongoRepository<Genre, String> {
}
