

/** @brief	The coms 309. game post */
package coms309.GamePost;

/** @brief	List of java.util.s */
import java.util.List;
/** @brief	The org.springframework.data.jpa.repository. jpa repository */
import org.springframework.data.jpa.repository.JpaRepository;
/** @brief	The org.springframework.stereotype. repository */
import org.springframework.stereotype.Repository;
/** @brief	The reactor.core.publisher. flux */
import reactor.core.publisher.Flux;
/** @brief	The reactor.core.publisher. mono */
import reactor.core.publisher.Mono;

/**********************************************************************************************//**
 * @interface	GamePostRepository
 *
 * @brief	Interface for game post repository.
 *
 * @author	Arie
 * @date	10/20/2023
 **************************************************************************************************/

@Repository
public interface GamePostRepository extends JpaRepository<GamePost, Integer> {

    /**********************************************************************************************//**
     * @fn	GamePost findById(int id);
     *
     * @brief	Searches for the first identifier
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	id	The identifier.
     *
     * @returns	The found identifier.
     **************************************************************************************************/

    GamePost findById(int id);

    /**********************************************************************************************//**
     * @fn	List<GamePost> findAll();
     *
     * @brief	Searches for the first all
     *
     * @author	Arie
     * @date	10/30/2023
     *
     * @returns	The found all.
     **************************************************************************************************/

    List<GamePost> findAll();
    // Flux<GamePost> findByLocation(int id);

    // Flux<GamePost> findAll();

    /**********************************************************************************************//**
     * @fn	void deleteById(int id);
     *
     * @brief	Deletes the by identifier described by ID
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	id	The identifier.
     **************************************************************************************************/

    void deleteById(int id);
    // Mono<GamePost> findByIdFlux(int id);
    // Flux<GamePost> findAllFlux();


    // List<GamePost> findByCreated_on(String created_on);
}
