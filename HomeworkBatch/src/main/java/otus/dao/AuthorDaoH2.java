package otus.dao;


import org.springframework.data.repository.CrudRepository;
import otus.model.Author;

public interface AuthorDaoH2 extends CrudRepository<Author, String> {

}
