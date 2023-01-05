package otus.dao.h2;


import org.springframework.data.repository.CrudRepository;
import otus.model.mongo.Comment;

public interface CommentDaoH2 extends CrudRepository<Comment, String> {

}
