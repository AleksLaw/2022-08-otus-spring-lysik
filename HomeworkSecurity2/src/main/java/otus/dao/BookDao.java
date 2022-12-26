package otus.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostFilter;
import otus.model.Book;

import java.util.List;

public interface BookDao extends JpaRepository<Book, Long> {

    @PostFilter("hasPermission(filterObject, 'READ')")
    List<Book> findAll();
}
