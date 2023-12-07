
package coms309.GamePost;

/** @brief	The org.reactivestreams. publisher */
import org.reactivestreams.Publisher;
/** @brief	The org.springframework.stereotype. component */
import org.springframework.stereotype.Component;
/** @brief	The coms 309. comment. comment */
import coms309.Comment.Comment;
/** @brief	The reactor.core.publisher. direct processor */
import reactor.core.publisher.DirectProcessor;
/** @brief	The reactor.core.publisher. flux processor */
import reactor.core.publisher.FluxProcessor;
/** @brief	The reactor.core.publisher. flux sink */
import reactor.core.publisher.FluxSink;

/**********************************************************************************************//**
 * @class	GamePostPublisher
 *
 * @brief	A game post publisher.
 *
 * @author	Arie
 * @date	10/30/2023
 **************************************************************************************************/

@Component
public class GamePostPublisher {

    /**********************************************************************************************//**
     * @property	private final FluxProcessor<GamePost, GamePost> processor
     *
     * @brief	Gets the processor
     *
     * @returns	The processor.
     **************************************************************************************************/

    private final FluxProcessor<GamePost, GamePost> processor;

    /**********************************************************************************************//**
     * @property	private final FluxSink<GamePost> sink
     *
     * @brief	Gets the sink
     *
     * @returns	The sink.
     **************************************************************************************************/

    private final FluxSink<GamePost> sink;

    // public Publisher<GamePost> subComments() {
    // return processor.map(gamePost -> {
    // return gamePost;
    // });
    // }

    /**********************************************************************************************//**
     * @fn	public GamePostPublisher()
     *
     * @brief	Default constructor
     *
     * @author	Arie
     * @date	10/30/2023
     **************************************************************************************************/

    public GamePostPublisher() {
        this.processor = DirectProcessor.<GamePost>create().serialize();
        this.sink = processor.sink();
    }

    /**********************************************************************************************//**
     * @fn	public void publish(GamePost gamePost)
     *
     * @brief	Publishes the given game post
     *
     * @author	Arie
     * @date	10/30/2023
     *
     * @param 	gamePost	The game post.
     **************************************************************************************************/

    public void publish(GamePost gamePost) {
        sink.next(gamePost);
    }

    /**********************************************************************************************//**
     * @fn	public Publisher<GamePost> subGamePost()
     *
     * @brief	Sub game post
     *
     * @author	Arie
     * @date	10/30/2023
     *
     * @returns	A Publisher&lt;GamePost&gt;
     **************************************************************************************************/

    public Publisher<GamePost> subGamePost() {
        return processor.map(gamePost -> {
            return gamePost;
        });
    }
}
