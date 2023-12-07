
package coms309.GamePost;

/** @brief	The org.springframework.beans.factory.annotation. autowired */
import org.springframework.beans.factory.annotation.Autowired;
/** @brief	The org.springframework.stereotype. service */
import org.springframework.stereotype.Service;
/** @brief	The lombok. required arguments constructor */
import lombok.RequiredArgsConstructor;
/** @brief	The reactor.core.publisher. flux */
import reactor.core.publisher.Flux;
/** @brief	The reactor.core.publisher. mono */
import reactor.core.publisher.Mono;

/**********************************************************************************************//**
 * @class	GamePostService
 *
 * @brief	A service for accessing game posts information.
 *
 * @author	Arie
 * @date	10/30/2023
 **************************************************************************************************/

@Service
@RequiredArgsConstructor
public class GamePostService {

    // @Autowired

    /**********************************************************************************************//**
     * @property	private final GamePostRepository gamePostRepository
     *
     * @brief	Gets the game post repository
     *
     * @returns	The game post repository.
     **************************************************************************************************/

    private final GamePostRepository gamePostRepository;
    
    // public Flux<GamePost> findAll() {
    //     return gamePostRepository.findAll();
    // }
    // public Mono<GamePost> findById(int id) {
    //     return gamePostRepository.findById(id);
    // }
    // public Flux<GamePost> findAllFlux(){
    //     return gamePostRepository.findAllFlux();
    // }

    /**********************************************************************************************//**
     * @fn	public Flux<GamePost> findAll()
     *
     * @brief	Searches for the first all
     *
     * @author	Arie
     * @date	10/30/2023
     *
     * @returns	The found all.
     **************************************************************************************************/

    public Flux<GamePost> findAll()
    {
        return Flux.fromIterable(gamePostRepository.findAll());
    }
}
