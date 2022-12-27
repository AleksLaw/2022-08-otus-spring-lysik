package otus.dao;


import org.springframework.data.repository.CrudRepository;
import otus.model.Comment;

public interface CommentDaoH2 extends CrudRepository<Comment, String> {

}
