
package coms309.Comment;

/** @brief The org.reactivestreams. publisher */
import org.reactivestreams.Publisher;
/** @brief The org.springframework.stereotype. component */
import org.springframework.stereotype.Component;
/** @brief The reactor.core.publisher. direct processor */
import reactor.core.publisher.DirectProcessor;
/** @brief The reactor.core.publisher. flux processor */
import reactor.core.publisher.FluxProcessor;
/** @brief The reactor.core.publisher. flux sink */
import reactor.core.publisher.FluxSink;

/**********************************************************************************************/
/**
 * @class CommentPublisher
 *
 * @brief A comment publisher.
 *
 * @author Arie
 * @date 10/30/2023
 **************************************************************************************************/

@Component
public class CommentPublisher {

    /**********************************************************************************************/
    /**
     * @property private final FluxProcessor<Comment, Comment> processor
     *
     * @brief Gets the processor
     *
     * @returns The processor.
     **************************************************************************************************/

    private final FluxProcessor<Comment, Comment> processor;

    /**********************************************************************************************/
    /**
     * @property private final FluxSink<Comment> sink
     *
     * @brief Gets the sink
     *
     * @returns The sink.
     **************************************************************************************************/

    private final FluxSink<Comment> sink;

    /**********************************************************************************************/
    /**
     * @fn public CommentPublisher()
     *
     * @brief Default constructor
     *
     * @author Arie
     * @date 10/30/2023
     **************************************************************************************************/

    public CommentPublisher() {
        this.processor = DirectProcessor.<Comment>create().serialize();
        this.sink = processor.sink();
    }

    /**********************************************************************************************/
    /**
     * @fn public void publish(Comment comment)
     *
     * @brief Publishes the given comment
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @param comment The comment.
     **************************************************************************************************/

    public void publish(Comment comment) {
        sink.next(comment);
    }

    /**********************************************************************************************/
    /**
     * @fn public Publisher<Comment> subCommentsById(int id)
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

    public Publisher<Comment> subCommentsById(int id) {
        return processor.map(comment -> {
            return comment;
        }).filter(comment -> comment.getId() == id);
    }

    /**********************************************************************************************/
    /**
     * @fn public Publisher<Comment> subComments()
     *
     * @brief Sub comments
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @returns A Publisher&lt;Comment&gt;
     **************************************************************************************************/

    public Publisher<Comment> subComments() {
        return processor.map((comment -> {
            return comment;
        }));
    }

}
