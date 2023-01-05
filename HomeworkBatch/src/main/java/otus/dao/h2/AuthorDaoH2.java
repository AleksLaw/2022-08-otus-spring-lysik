package otus.dao.h2;


import org.springframework.data.repository.CrudRepository;
import otus.model.mongo.Author;

public interface AuthorDaoH2 extends CrudRepository<Author, String> {

}
