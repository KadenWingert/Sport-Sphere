

/** @brief The coms 309. comment */
package coms309.Comment;

import coms309.CommentMessage.CommentMessage;
import coms309.CommentMessage.CommentMessageRepository;
/** @brief The coms 309. exceptions. not found exception */
import coms309.Exceptions.NotFoundException;
/** @brief The coms 309. game post. game post */
import coms309.GamePost.GamePost;
/** @brief The coms 309. game post. game post repository */
import coms309.GamePost.GamePostRepository;
import coms309.Users.UserRepository;
import coms309.Users.Users;
/** @brief The org.reactivestreams. publisher */
import org.reactivestreams.Publisher;
/** @brief The org.springframework.beans.factory.annotation. autowired */
import org.springframework.beans.factory.annotation.Autowired;
/** @brief The org.springframework.graphql.data.method.annotation. argument */
import org.springframework.graphql.data.method.annotation.Argument;
/** @brief The org.springframework.graphql.data.method.annotation. mutation mapping */
import org.springframework.graphql.data.method.annotation.MutationMapping;
/** @brief The org.springframework.graphql.data.method.annotation. query mapping */
import org.springframework.graphql.data.method.annotation.QueryMapping;
/** @brief The org.springframework.graphql.data.method.annotation. subscription mapping */
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
/** @brief The org.springframework.stereotype. component */
import org.springframework.stereotype.Component;
/** @brief The org.springframework.stereotype. controller */
import org.springframework.stereotype.Controller;
/** @brief List of java.util.s */
import java.util.List;

/**********************************************************************************************/
/**
 * @class CommentController
 *
 * @brief A controller for handling comments.
 *
 * @author Arie
 * @date 10/20/2023
 **************************************************************************************************/

/**********************************************************************************************/
/**
 * @class CommentController
 *
 * @brief A controller for handling comments.
 *
 * @author Arie
 * @date 10/30/2023
 **************************************************************************************************/

@Controller
@Component
public class CommentController {

    /**********************************************************************************************/
    /**
     * @property @Autowired CommentRepository commentRepository
     *
     * @brief Gets the comment repository
     *
     * @returns The comment repository.
     **************************************************************************************************/

    /**********************************************************************************************/
    /**
     * @property @Autowired CommentRepository commentRepository
     *
     * @brief Gets the comment repository
     *
     * @returns The comment repository.
     **************************************************************************************************/

    @Autowired
    CommentRepository commentRepository;


    /**********************************************************************************************/
    /**
     * @property @Autowired GamePostRepository gamePostRepository
     *
     * @brief Gets the game post repository
     *
     * @returns The game post repository.
     **************************************************************************************************/

    @Autowired
    GamePostRepository gamePostRepository;

    /**********************************************************************************************/
    /**
     * @property @Autowired CommentPublisher commentPublisher
     *
     * @brief Gets the comment publisher
     *
     * @returns The comment publisher.
     **************************************************************************************************/

    @Autowired
    CommentPublisher commentPublisher;

    @Autowired
    CommentMessageRepository commentMessageRepository;

    @Autowired
    UserRepository userRepository;

    /**********************************************************************************************/
    /**
     * @fn @QueryMapping public List<Comment> getAllComments()
     *
     * @brief Gets all comments
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @returns all comments.
     **************************************************************************************************/

    @QueryMapping
    public List<Comment> getAllComments() {

        return commentRepository.findAll();
    }

    /**********************************************************************************************/
    /**
     * @fn @QueryMapping public Comment getCommentById(@Argument int id)
     *
     * @brief Gets comment by identifier
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @exception NotFoundException Thrown when the requested element is not present.
     *
     * @param id The identifier.
     *
     * @returns The comment by identifier.
     **************************************************************************************************/

    @QueryMapping
    public Comment getCommentById(@Argument int id) {
        Comment comment = commentRepository.findById((id));
        if (comment == null) {
            throw new NotFoundException("Comment with ID " + id + " not found");
        }
        return comment;
    }


    /**********************************************************************************************/
    /**
     * @fn @MutationMapping public Comment createComment(@Argument int game_post_id, @Argument
     *     String comment_message)
     *
     * @brief Creates a comment
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @exception NotFoundException Thrown when the requested element is not present.
     *
     * @param game_post_id Identifier for the game post.
     * @param comment_message Message describing the comment.
     * 
     * @returns The new comment.
     **************************************************************************************************/

    @MutationMapping
    public Comment createComment(@Argument int game_post_id, @Argument String comment_details,
            @Argument int user_id) {
        GamePost gamePost = gamePostRepository.findById(game_post_id);
        Users userID = userRepository.findById(user_id);

        if (gamePost == null) {
            throw new NotFoundException("Could not find Game Post with id: " + game_post_id);
        }

        Comment comment = new Comment(gamePost, userID);
        commentRepository.save(comment);

        CommentMessage commentMessage = new CommentMessage(comment_details, comment);
        commentMessageRepository.save(commentMessage);

        Comment result = commentRepository.findById(comment.getId());
        if (result != null) {
            commentPublisher.publish(result);
        }
        // return comment;
        return result;
    }


    /**********************************************************************************************/
    /**
     * @fn @MutationMapping public Comment updateComment(@Argument int comment_id, @Argument String
     *     new_message)
     *
     * @brief Updates the comment
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @exception NotFoundException Thrown when the requested element is not present.
     *
     * @param comment_id Identifier for the comment.
     * @param new_message Message describing the new.
     *
     * @returns A Comment.
     **************************************************************************************************/

    @MutationMapping
    public Comment updateComment(@Argument int comment_id, @Argument String new_message) {
        Comment comment = commentRepository.findById(comment_id);
        if (comment == null)
            throw new NotFoundException("Could not find comment with id: " + comment_id);
        if (new_message != null) {
            CommentMessage commentMessage = comment.getComment_details();
            commentMessage.setMessage(new_message);
            comment.setMessage(commentMessage);
            commentMessageRepository.save(commentMessage);
        }
        commentRepository.save(comment);
        Comment result = commentRepository.findById(comment_id);

        commentPublisher.publish(result);
        return result;
    }


    /**********************************************************************************************/
    /**
     * @fn @MutationMapping public Comment deleteCommentById(@Argument int id)
     *
     * @brief Deletes the comment by identifier described by ID
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @exception NotFoundException Thrown when the requested element is not present.
     *
     * @param id The identifier.
     *
     * @returns A Comment.
     **************************************************************************************************/

    @MutationMapping
    public Comment deleteCommentById(@Argument int id) {
        Comment comment = commentRepository.findById(id);
        if (comment == null) {
            throw new NotFoundException("GamePost not found.");
        }
        GamePost gamePost = comment.getGamePost();
        gamePost.getComment().remove(comment);
        commentRepository.save(comment);
        commentPublisher.publish(comment);
        return comment;
    }


    /**********************************************************************************************/
    /**
     * @fn @MutationMapping public String deleteAllComments()
     *
     * @brief Deletes all comments
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @returns A String.
     **************************************************************************************************/

    @MutationMapping
    public String deleteAllComments() {
        for (Comment c : commentRepository.findAll()) {
            c.getGamePost().getComment().remove(c);
            gamePostRepository.save(c.getGamePost());
        }
        commentRepository.deleteAll();
        return "All Comments have been deleted";
    }

    /**********************************************************************************************/
    /**
     * @fn @SubscriptionMapping public Publisher<Comment> subComments()
     *
     * @brief Sub comments
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @returns A Publisher&lt;Comment&gt;
     **************************************************************************************************/

    @SubscriptionMapping
    public Publisher<Comment> subComments() {
        return commentPublisher.subComments();
    }

    /**********************************************************************************************/
    /**
     * @fn @SubscriptionMapping public Publisher<Comment> subCommentsById(int id)
     *
     * @brief Sub comments by identifier
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @param id The identifier.
     *
     * @returns A Publisher&lt;Comment&gt;
     **************************************************************************************************/

    @SubscriptionMapping
    public Publisher<Comment> subCommentsById(@Argument int id) {
        return commentPublisher.subCommentsById(id);
    }
}
