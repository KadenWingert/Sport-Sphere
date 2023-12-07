

/** @brief The coms 309. game post */
package coms309.GamePost;

/** @brief The coms 309. auth. authentication */
import coms309.Auth.Auth;
/** @brief The coms 309. exceptions. duplicate exception */
import coms309.Exceptions.DuplicateException;
/** @brief The coms 309. exceptions. not found exception */
import coms309.Exceptions.NotFoundException;
/** @brief The coms 309. location. location */
import coms309.Location.Location;
/** @brief The coms 309. location. location repository */
import coms309.Location.LocationRepository;
/** @brief The coms 309. sport. sport */
import coms309.Sport.Sport;
/** @brief The coms 309. sport. sport repository */
import coms309.Sport.SportRepository;
/** @brief The coms 309. users. users */
import coms309.Users.Users;
/** @brief The graphql.com.google.common.base. optional */
import graphql.com.google.common.base.Optional;
/** @brief The coms 309. comment. comment */
import coms309.Comment.Comment;
/** @brief The coms 309. comment. comment repository */
import coms309.Comment.CommentRepository;
/** @brief The coms 309. users. user repository */
import coms309.Users.UserRepository;
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
/** @brief The org.springframework.stereotype. controller */
import org.springframework.stereotype.Controller;
/** @brief The reactor.core.publisher. flux */
import reactor.core.publisher.Flux;
/** @brief The reactor.core.publisher. flux sink */
import reactor.core.publisher.FluxSink;
/** @brief The reactor.core.publisher. mono */
import reactor.core.publisher.Mono;
/** @brief Stack of org.hibernate.internal.util.collections.s */
import org.hibernate.internal.util.collections.Stack;
/** @brief The org.reactivestreams. publisher */
import org.reactivestreams.Publisher;
// import graphiql.kickstart.tools.GraphQLSubscriptionResolver;
// import graphiql.schema.GraphQLSubscription;
/** @brief List of java.util. arrays */
import java.util.ArrayList;
/** @brief List of java.util.s */
import java.util.List;
/** @brief Set the java.util. belongs to */
import java.util.Set;
/** @brief Duration of the java.time. */
import java.time.Duration;


/**********************************************************************************************/
/**
 * @class GamePostController
 *
 * @brief A controller for handling game posts.
 *
 * @author Arie
 * @date 10/30/2023
 **************************************************************************************************/

@Controller
public class GamePostController {


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
     * @property @Autowired UserRepository userRepository
     *
     * @brief Gets the user repository
     *
     * @returns The user repository.
     **************************************************************************************************/

    @Autowired
    UserRepository userRepository;


    /**********************************************************************************************/
    /**
     * @property @Autowired LocationRepository locationRepository
     *
     * @brief Gets the location repository
     *
     * @returns The location repository.
     **************************************************************************************************/

    @Autowired
    LocationRepository locationRepository;


    /**********************************************************************************************/
    /**
     * @property @Autowired SportRepository sportRepository
     *
     * @brief Gets the sport repository
     *
     * @returns The sport repository.
     **************************************************************************************************/

    @Autowired
    SportRepository sportRepository;

    /**********************************************************************************************/
    /**
     * @property @Autowired GamePostPublisher gamePostPublisher
     *
     * @brief Gets the game post publisher
     *
     * @returns The game post publisher.
     **************************************************************************************************/

    @Autowired
    GamePostPublisher gamePostPublisher;

    /**********************************************************************************************/
    /**
     * @fn @QueryMapping public List<GamePost> getAllGamePosts()
     *
     * @brief Gets all game posts
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @returns all game posts.
     **************************************************************************************************/

    @QueryMapping
    public List<GamePost> getAllGamePosts() {
        List<GamePost> results = gamePostRepository.findAll();
        return results;
    }


    /**********************************************************************************************/
    /**
     * @fn @QueryMapping public GamePost getGamePostById(@Argument int id)
     *
     * @brief Gets game post by identifier
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @exception NotFoundException Thrown when the requested element is not present.
     *
     * @param id The identifier.
     *
     * @returns The game post by identifier.
     **************************************************************************************************/

    @QueryMapping
    public GamePost getGamePostById(@Argument int id) {
        GamePost gamePost = gamePostRepository.findById((id));
        if (gamePost == null) {
            throw new NotFoundException("GamePost with ID " + id + " not found");

        }
        return gamePost;

    }


    /**********************************************************************************************/
    /**
     * @fn @QueryMapping public Set<Users> getUsersPlaying(@Argument int gamePostID)
     *
     * @brief Gets users playing
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @exception NotFoundException Thrown when the requested element is not present.
     *
     * @param gamePostID Identifier for the game post.
     *
     * @returns The users playing.
     **************************************************************************************************/

    @QueryMapping
    public Set<Users> getUsersPlaying(@Argument int gamePostID) {
        GamePost gamePost = gamePostRepository.findById(gamePostID);
        if (gamePost == null) {
            throw new NotFoundException("User or Game Post id not found");
        }
        return gamePost.getPlayersSignedUp();
    }

    // @QueryMapping
    // public List<GamePost> getGamePostsByDate(@Argument String date) {
    // List<GamePost> resultList = gamePostRepository.findByCreated_on(date);

    // return resultList;
    // }

    /**********************************************************************************************/
    /**
     * @property @Argument String GPS, @Argument String email, @Argument String password)
     *
     * @brief Gets the password)
     *
     * @exception NotFoundException Thrown when the requested element is not present.
     *
     * @returns The password)
     **************************************************************************************************/
    @MutationMapping
    public GamePost createGamePost(@Argument int sport_id, @Argument int created_by,
            @Argument int max_players, @Argument int min_players, @Argument String playing_on,
            @Argument String created_on, @Argument boolean is_deleted, @Argument String address,
            @Argument String GPS, @Argument String email, @Argument String password) {

        Users postCreator = userRepository.findById(created_by);
        Sport sport = sportRepository.findById(sport_id);
        GamePost gamePost = null;

        if (postCreator != null && Auth.isAuthenticated(postCreator, email, password)) {
            if (sport == null) {
                throw new NotFoundException("Sport with id: " + sport_id + " not found");
            }
            Location location = new Location(address, GPS);
            gamePost = new GamePost(sport, postCreator, max_players, min_players, playing_on,
                    created_on, is_deleted);
            gamePost.setLocation(location);
            gamePost.setPlayersSignedUp(postCreator);

        } else if (postCreator == null) {
            throw new NotFoundException("User with id: " + created_by + " not found");
        }

        Location location = new Location(address, GPS);
        gamePost = new GamePost(sport, postCreator, max_players, min_players, playing_on,
                created_on, is_deleted);
        gamePost.setLocation(location);
        gamePostPublisher.publish(gamePost);
        return gamePostRepository.save(gamePost);
    }

    /**********************************************************************************************/
    /**
     * @property @Argument String GPS, @Argument String email, @Argument String password)
     *
     * @brief Gets the password)
     *
     * @returns The password)
     **************************************************************************************************/
    @MutationMapping
    public GamePost updateGamePost(@Argument int gamePostID, @Argument Integer sport_id,
            @Argument Integer max_players, @Argument Integer min_players,
            @Argument String playing_on, @Argument String created_on, @Argument Boolean is_deleted,
            @Argument String address, @Argument String GPS, @Argument String email,
            @Argument String password) {

        GamePost gamePost = gamePostRepository.findById(gamePostID);

        // only the creator of the game post can make changes
        Users user = userRepository.findById(gamePost.getCreated_by().getId());

        if (Auth.isAuthenticated(user, email, password)) {
            if (sport_id != null && sport_id > 0) {
                Sport sport = sportRepository.findById(sport_id.intValue());
                gamePost.setSport(sport);
            }

            if (max_players != null && max_players > 0) {
                gamePost.setMax_players(max_players);
            }
            if (min_players != null && min_players > 0) {
                gamePost.setMin_players(min_players);
            }
            if (playing_on != null) {
                gamePost.setPlaying_on(playing_on);
            }
            if (created_on != null) {
                gamePost.setCreated_on(created_on);
            }
            if (is_deleted != null) {
                gamePost.setIs_deleted(is_deleted);
            }
            if (address != null && GPS != null) {
                gamePost.getLocation().setAddress(address);
                gamePost.getLocation().setGps(GPS);
                locationRepository.save(gamePost.getLocation());

            }
        }

        gamePostPublisher.publish(gamePost);

        return gamePostRepository.save(gamePost);
    }


    /**********************************************************************************************/
    /**
     * @property @Argument String email, @Argument String password)
     *
     * @brief Gets the password)
     *
     * @exception NotFoundException Thrown when the requested element is not present.
     * @exception DuplicateException Thrown when a Duplicate error condition occurs.
     *
     * @returns The password)
     **************************************************************************************************/
    @MutationMapping
    public Set<Users> addNewUserPlaying(@Argument int gamePostID, @Argument int userID,

            /**********************************************************************************************//**
                                                                                                             * @property @Argument
                                                                                                             *           String
                                                                                                             *           email, @Argument
                                                                                                             *           String
                                                                                                             *           password)
                                                                                                             *
                                                                                                             * @brief Gets
                                                                                                             *        the
                                                                                                             *        password)
                                                                                                             *
                                                                                                             * @exception NotFoundException
                                                                                                             *            Thrown
                                                                                                             *            when
                                                                                                             *            the
                                                                                                             *            requested
                                                                                                             *            element
                                                                                                             *            is
                                                                                                             *            not
                                                                                                             *            present.
                                                                                                             * @exception DuplicateException
                                                                                                             *            Thrown
                                                                                                             *            when
                                                                                                             *            a
                                                                                                             *            Duplicate
                                                                                                             *            error
                                                                                                             *            condition
                                                                                                             *            occurs.
                                                                                                             *
                                                                                                             * @returns The
                                                                                                             *          password)
                                                                                                             **************************************************************************************************/

            @Argument String email, @Argument String password) {

        Users user = userRepository.findById(userID);
        GamePost gamePost = gamePostRepository.findById(gamePostID);

        if (Auth.isAuthenticated(user, email, password)) {
            if (user == null || gamePost == null) {
                throw new NotFoundException("User or Game Post id not found");
            }
            if (gamePost.getPlayersSignedUp().contains(user)) {
                throw new DuplicateException("You are already signed up to play");
            }
            gamePost.setPlayersSignedUp(user);

            gamePostRepository.save(gamePost);
        }
        gamePostPublisher.publish(gamePost);

        return gamePost.getPlayersSignedUp();
    }

    /**********************************************************************************************/
    /**
     * @property @Argument String email, @Argument String password)
     *
     * @brief Gets the password)
     *
     * @exception NotFoundException Thrown when the requested element is not present.
     *
     * @returns The password)
     **************************************************************************************************/
    @MutationMapping
    public GamePost addGamePostToSport(@Argument int gamePost_id, @Argument int sport_id,

            /**********************************************************************************************//**
                                                                                                             * @property @Argument
                                                                                                             *           String
                                                                                                             *           email, @Argument
                                                                                                             *           String
                                                                                                             *           password)
                                                                                                             *
                                                                                                             * @brief Gets
                                                                                                             *        the
                                                                                                             *        password)
                                                                                                             *
                                                                                                             * @exception NotFoundException
                                                                                                             *            Thrown
                                                                                                             *            when
                                                                                                             *            the
                                                                                                             *            requested
                                                                                                             *            element
                                                                                                             *            is
                                                                                                             *            not
                                                                                                             *            present.
                                                                                                             *
                                                                                                             * @returns The
                                                                                                             *          password)
                                                                                                             **************************************************************************************************/

            @Argument String email, @Argument String password) {
        GamePost gamePost = gamePostRepository.findById(gamePost_id);

        // Only the creator may make changes
        Users user = userRepository.findById(gamePost.getCreated_by().getId());
        if (Auth.isAuthenticated(user, email, password)) {
            Sport sport = sportRepository.findById(sport_id);
            if (sport == null) {
                throw new NotFoundException("Sport with ID" + sport + "not found.");
            }
            sport.getGamePosts().add(gamePost);
            sportRepository.save(sport);
            gamePost.setSport(sport);
            gamePostRepository.save(gamePost);
        }
        gamePostPublisher.publish(gamePost);

        return gamePost;
    }

    /**********************************************************************************************/
    /**
     * @property @Argument String email, @Argument String password)
     *
     * @brief Gets the password)
     *
     * @returns The password)
     **************************************************************************************************/
    @MutationMapping
    public Set<Users> removeUserPlaying(@Argument int gamePostID, @Argument int userID,

            /**********************************************************************************************//**
                                                                                                             * @property @Argument
                                                                                                             *           String
                                                                                                             *           email, @Argument
                                                                                                             *           String
                                                                                                             *           password)
                                                                                                             *
                                                                                                             * @brief Gets
                                                                                                             *        the
                                                                                                             *        password)
                                                                                                             *
                                                                                                             * @returns The
                                                                                                             *          password)
                                                                                                             **************************************************************************************************/

            @Argument String email, @Argument String password) {
        Users user = userRepository.findById(userID);
        GamePost gamePost = gamePostRepository.findById(gamePostID);

        // Users may remove themselves or a game owner may remove other users
        if (Auth.isAuthenticated(user, email, password)
                || gamePost.getCreated_by().getId() == user.getId()) {
            // gamePost.getPlayersSignedUp().remove(user);
            gamePost.removePlayerSignedUp(user);

            gamePostRepository.save(gamePost);
        }

        gamePostPublisher.publish(gamePost);

        return gamePost.getPlayersSignedUp();
    }


    /**********************************************************************************************/
    /**
     * @property @Argument String password)
     *
     * @brief Gets the password)
     *
     * @returns The password)
     **************************************************************************************************/
    @MutationMapping
    public String deleteGamePostById(@Argument int id, @Argument String email,

            /**********************************************************************************************//**
                                                                                                             * @property @Argument
                                                                                                             *           String
                                                                                                             *           password)
                                                                                                             *
                                                                                                             * @brief Gets
                                                                                                             *        the
                                                                                                             *        password)
                                                                                                             *
                                                                                                             * @returns The
                                                                                                             *          password)
                                                                                                             **************************************************************************************************/

            @Argument String password) {
        GamePost gamePost = gamePostRepository.findById(id);
        Users user = userRepository.findById(gamePost.getCreated_by().getId());

        if (Auth.isAuthenticated(user, email, password)) {
            gamePost.getPlayersSignedUp().clear();
            gamePostRepository.deleteById(id);
            gamePostPublisher.publish(gamePost);

            return "Successfully deleted GamePosts with id " + id;
        }
        return "Unable to delete this gamePost";

    }


    /**********************************************************************************************/
    /**
     * @fn @MutationMapping public String deleteAllGamePosts(@Argument String email, @Argument
     *     String password)
     *
     * @brief Deletes all game posts
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @param email The email.
     * @param password The password.
     *
     * @returns A String.
     **************************************************************************************************/

    @MutationMapping
    public String deleteAllGamePosts(@Argument String email, @Argument String password) {

        Users user = userRepository.findByEmail(email);
        String result = "Unauthorized";

        if (Auth.isAuthenticated(user, email, password) && Auth.isAdmin(user)) {
            gamePostRepository.deleteAll();
            result = "Successfully deleted all GamePosts";
        }

        return result;
    }

    /**********************************************************************************************//**
                                                                                                     * @fn @SubscriptionMapping
                                                                                                     *     public
                                                                                                     *     Flux<Integer>
                                                                                                     *     getCommentByPost(int
                                                                                                     *     gamePostId)
                                                                                                     *
                                                                                                     * @brief Gets
                                                                                                     *        comment
                                                                                                     *        by
                                                                                                     *        post
                                                                                                     *
                                                                                                     * @author Arie
                                                                                                     * @date 10/30/2023
                                                                                                     *
                                                                                                     * @param gamePostId
                                                                                                     *        Identifier
                                                                                                     *        for
                                                                                                     *        the
                                                                                                     *        game
                                                                                                     *        post.
                                                                                                     *
                                                                                                     * @returns The
                                                                                                     *          comment
                                                                                                     *          by
                                                                                                     *          post.
                                                                                                     **************************************************************************************************/

    // @SubscriptionMapping
    // public Flux<Integer> subCommentByPost(int gamePostId) {

    // GamePost gamePost = gamePostRepository.findById(gamePostId);

    // // Users user = userRepository.findById((id));
    // // return user.getUserStream();
    // // return Flux.fromIterable(gamePost.getComment());
    // // return Flux.create(subscriber-> subscriber.add(taskId, subscriber.onDispose(()->
    // // subscriber.remove(taskId, subscriber))), FluxSink.OverflowStrategy.LATEST);
    // // To Stream data and call subscribe method
    // // List<GamePost> dataStream = gamePostRepository.findAll();

    // // return Flux.from(gamePostRepository.findAll()).subscribe(dataStream::add);

    // // return gamePostRepository.findAll();


    // return Flux.just(gamePost.getId());
    // }


    /**********************************************************************************************/
    /**
     * @fn @SubscriptionMapping public Publisher<GamePost> subGamePost()
     *
     * @brief Sub game post
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @returns A Publisher&lt;GamePost&gt;
     **************************************************************************************************/

    @SubscriptionMapping
    public Publisher<GamePost> subGamePost() {
        return gamePostPublisher.subGamePost();
    }
}
