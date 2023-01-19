package otus.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import otus.model.Genre;

public interface GenreDao extends JpaRepository<Genre, Long> {

}
