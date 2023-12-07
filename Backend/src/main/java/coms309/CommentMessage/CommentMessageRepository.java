package coms309.CommentMessage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import coms309.Comment.Comment;

@Repository
public interface CommentMessageRepository extends JpaRepository<CommentMessage, Integer> {

    



}
