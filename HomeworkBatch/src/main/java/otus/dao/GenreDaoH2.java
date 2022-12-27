package otus.dao;


import org.springframework.data.repository.CrudRepository;
import otus.model.Genre;

public interface GenreDaoH2 extends CrudRepository<Genre, String> {

}
