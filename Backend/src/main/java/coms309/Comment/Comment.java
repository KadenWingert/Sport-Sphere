

/** @brief The coms 309. comment */
package coms309.Comment;

import coms309.CommentMessage.CommentMessage;
/** @brief The coms 309. game post. game post */
import coms309.GamePost.GamePost;
import coms309.Users.Users;
/** @brief The jakarta.persistence.* */
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
/** @brief The lombok. no arguments constructor */
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**********************************************************************************************/
/**
 * @class Comment
 *
 * @brief A comment.
 *
 * @author Arie
 * @date 10/20/2023
 **************************************************************************************************/

@Entity
// @NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "comment")
public class Comment {

    /**********************************************************************************************/
    /**
     * @fn @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id") private int
     *     id;
     *
     * @brief Constructor
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param parameter1 (Optional) The first parameter.
     **************************************************************************************************/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne(mappedBy = "comment", fetch = FetchType.LAZY, optional = false)
    // @JoinColumn(name = "message_id", nullable = false)
    private CommentMessage comment_details;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    /**********************************************************************************************/
    /**
     * @fn @ManyToOne @JoinColumn(name = "game_post_id") private GamePost gamePost;
     *
     * @brief Constructor
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param parameter1 (Optional) The first parameter.
     **************************************************************************************************/

    @ManyToOne
    @JoinColumn(name = "game_post_id")
    private GamePost gamePost;

    /**********************************************************************************************/
    /**
     * @fn public Comment(String message, GamePost gamePost)
     *
     * @brief Constructor
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param message The message.
     * @param gamePost The game post.
     * @param userID2
     **************************************************************************************************/

    public Comment(GamePost gamePost, Users user) {
        this.gamePost = gamePost;
        this.user = user;
    }

    /**********************************************************************************************/
    /**
     * @fn public String getMessage()
     *
     * @brief Gets the message
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @returns String.
     **************************************************************************************************/

    public CommentMessage getMessage() {
        return comment_details;
    }

    /**********************************************************************************************/
    /**
     * @fn public void setMessage(String message)
     *
     * @brief Sets a message
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param message .
     **************************************************************************************************/

    public void setMessage(CommentMessage message) {
        this.comment_details = message;
    }

    /**********************************************************************************************/
    /**
     * @fn public int getId()
     *
     * @brief Gets the identifier
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @returns The identifier.
     **************************************************************************************************/

    public int getId() {
        return id;
    }

    /**********************************************************************************************/
    /**
     * @fn public void setId(int comment_id)
     *
     * @brief Sets an identifier
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param comment_id Identifier for the comment.
     **************************************************************************************************/

    public void setId(int comment_id) {
        this.id = comment_id;
    }

    /**********************************************************************************************/
    /**
     * @fn public GamePost getGamePost()
     *
     * @brief Gets game post
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @returns The game post.
     **************************************************************************************************/

    public GamePost getGamePost() {
        return gamePost;
    }

    /**********************************************************************************************/
    /**
     * @fn public void setGamePost(GamePost gamePost)
     *
     * @brief Sets game post
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param gamePost The game post.
     **************************************************************************************************/

    public void setGamePost(GamePost gamePost) {
        this.gamePost = gamePost;
    }

    public Comment(GamePost gp1, Users u1, CommentMessage cm) {
        this.gamePost = gp1;
        this.user = u1;
        this.comment_details = cm;
    }
}
