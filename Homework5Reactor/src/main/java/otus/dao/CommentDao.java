package otus.dao;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import otus.model.Comment;

public interface CommentDao extends ReactiveMongoRepository<Comment, String> {
}
