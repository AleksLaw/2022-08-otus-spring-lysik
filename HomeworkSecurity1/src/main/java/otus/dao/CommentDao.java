package otus.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import otus.model.Comment;

public interface CommentDao extends JpaRepository<Comment, Long> {

}
