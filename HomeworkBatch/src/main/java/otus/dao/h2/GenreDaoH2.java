package otus.dao.h2;


import org.springframework.data.repository.CrudRepository;
import otus.model.mongo.Genre;

public interface GenreDaoH2 extends CrudRepository<Genre, String> {

}
