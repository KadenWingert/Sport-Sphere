
package coms309.Users;

/** @brief	The org.reactivestreams. publisher */
import org.reactivestreams.Publisher;
/** @brief	The org.springframework.stereotype. component */
import org.springframework.stereotype.Component;
/** @brief	The lombok. no arguments constructor */
import lombok.NoArgsConstructor;
/** @brief	The reactor.core.publisher. direct processor */
import reactor.core.publisher.DirectProcessor;
/** @brief	The reactor.core.publisher. flux processor */
import reactor.core.publisher.FluxProcessor;
/** @brief	The reactor.core.publisher. flux sink */
import reactor.core.publisher.FluxSink;
/** @brief	The reactor.core.publisher. mono */
import reactor.core.publisher.Mono;

/**********************************************************************************************//**
 * @class	UsersPublisher
 *
 * @brief	The users publisher.
 *
 * @author	Arie
 * @date	10/30/2023
 **************************************************************************************************/

@Component
public class UsersPublisher {

    /**********************************************************************************************//**
     * @property	private final FluxProcessor<Users, Users> processor
     *
     * @brief	Gets the processor
     *
     * @returns	The processor.
     **************************************************************************************************/

    private final FluxProcessor<Users, Users> processor;

    /**********************************************************************************************//**
     * @property	private final FluxSink<Users> sink
     *
     * @brief	Gets the sink
     *
     * @returns	The sink.
     **************************************************************************************************/

    private final FluxSink<Users> sink;

    /**********************************************************************************************//**
     * @fn	public UsersPublisher()
     *
     * @brief	Default constructor
     *
     * @author	Arie
     * @date	10/30/2023
     **************************************************************************************************/

    public UsersPublisher() {
        this.processor = DirectProcessor.<Users>create().serialize();
        this.sink = processor.sink();
    }

    /**********************************************************************************************//**
     * @fn	public Publisher<Users> getUsers()
     *
     * @brief	Gets the users
     *
     * @author	Arie
     * @date	10/30/2023
     *
     * @returns	The users.
     **************************************************************************************************/

    public Publisher<Users> getUsers() {
        return processor.map(user -> {
            return user;
        });
    }

    /**********************************************************************************************//**
     * @fn	public void publish(Users user)
     *
     * @brief	Publishes the given user
     *
     * @author	Arie
     * @date	10/30/2023
     *
     * @param 	user	The user.
     **************************************************************************************************/

    public void publish(Users user) {
        sink.next(user);
    }

}
