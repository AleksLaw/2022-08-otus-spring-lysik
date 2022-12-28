package otus.dao.mongo;


import org.springframework.data.mongodb.repository.MongoRepository;
import otus.model.mongo.Comment;

public interface CommentDao extends MongoRepository<Comment, String> {

}
