package otus.dao;


import org.springframework.data.mongodb.repository.MongoRepository;
import otus.model.Comment;

public interface CommentDao extends MongoRepository<Comment, String> {

}
