/** @brief The coms 309. users */
package coms309.Users;

/** @brief The coms 309. auth. authentication */
import coms309.Auth.Auth;
/** @brief The coms 309. comment. comment */
import coms309.Comment.Comment;
/** @brief The coms 309. comment. comment repository */
import coms309.Comment.CommentRepository;
import coms309.CommentMessage.CommentMessage;
import coms309.CommentMessage.CommentMessageRepository;
/** @brief The coms 309. email. email sender */
import coms309.Email.EmailSender;
/** @brief The coms 309. enums. permission.permission */
import coms309.Enums.Permission.permission;
/** @brief The coms 309. exceptions. duplicate exception */
import coms309.Exceptions.DuplicateException;
/** @brief The coms 309. exceptions. not found exception */
import coms309.Exceptions.NotFoundException;
/** @brief The coms 309. game post. game post */
import coms309.GamePost.GamePost;
/** @brief The coms 309. game post. game post repository */
import coms309.GamePost.GamePostRepository;
/** @brief The coms 309. location. location */
import coms309.Location.Location;
import coms309.PreferenceValue.PreferenceValueRepository;
/** @brief The coms 309. skill level. skill level */
import coms309.SkillLevel.SkillLevel;
/** @brief The coms 309. skill level. skill level repository */
import coms309.SkillLevel.SkillLevelRepository;
/** @brief The coms 309. sport. sport */
import coms309.Sport.Sport;
/** @brief The coms 309. sport. sport repository */
import coms309.Sport.SportRepository;
/** @brief The graphql. graph ql error */
import graphql.GraphQLError;
import graphql.GraphQLException;
import jakarta.transaction.Transactional;
/** @brief The reactor.core.publisher. flux */
import reactor.core.publisher.Flux;
/** @brief The reactor.core.publisher. mono */
import reactor.core.publisher.Mono;
/** @brief The org.springframework.beans.factory.annotation. autowired */
import org.springframework.beans.factory.annotation.Autowired;
/** @brief The org.springframework.graphql.data.method.annotation. argument */
import org.springframework.graphql.data.method.annotation.Argument;
/** @brief The org.springframework.graphql.data.method.annotation. graph ql exception handler */
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
/** @brief The org.springframework.graphql.data.method.annotation. mutation mapping */
import org.springframework.graphql.data.method.annotation.MutationMapping;
/** @brief The org.springframework.graphql.data.method.annotation. query mapping */
import org.springframework.graphql.data.method.annotation.QueryMapping;
/** @brief The org.springframework.graphql.data.method.annotation. subscription mapping */
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
/** @brief Type of the org.springframework.graphql.execution. error */
import org.springframework.graphql.execution.ErrorType;
/** @brief The org.springframework.stereotype. controller */
import org.springframework.stereotype.Controller;
/** @brief The org.reactivestreams. publisher */
import org.reactivestreams.Publisher;
/** @brief The org.slf 4j. logger */
import org.slf4j.Logger;
/** @brief The org.slf 4j. logger factory */
import org.slf4j.LoggerFactory;
/** @brief The java.net. bind exception */
import java.net.BindException;
/** @brief Duration of the java.time. */
import java.time.Duration;
/** @brief Set the java.util. hash belongs to */
import java.util.HashSet;
/** @brief Set the java.util. belongs to */
import java.util.Set;

/** @brief List of java.util.s */
import java.util.List;


/**********************************************************************************************/
/**
 * @class UserController
 *
 * @brief A controller for handling users.
 *
 * @author Arie
 * @date 10/30/2023
 **************************************************************************************************/

@Controller
@Transactional
public class UserController {

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
     * @property @Autowired SkillLevelRepository skillLevelRepository
     *
     * @brief Gets the skill level repository
     *
     * @returns The skill level repository.
     **************************************************************************************************/

    @Autowired
    SkillLevelRepository skillLevelRepository;

    /** @brief /** @brief The logger */
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**********************************************************************************************/
    /**
     * @property @Autowired UsersPublisher usersPublisher
     *
     * @brief Gets the users publisher
     *
     * @returns The users publisher.
     **************************************************************************************************/

    @Autowired
    UsersPublisher usersPublisher;

    @Autowired
    PreferenceValueRepository preferenceValueRepository;

    @Autowired
    CommentMessageRepository commentMessageRepository;

    /**********************************************************************************************/
    /**
     * @fn @MutationMapping public String createDummyData()
     *
     * @brief Creates dummy data
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @returns The new dummy data.
     **************************************************************************************************/

    @MutationMapping
    public String createDummyData() {

        Users u1 = new Users("Mark", "Heath", "Mheath@gmail.com", "batman100");
        Users u2 = new Users("Phil", "Kittle", "pkittle@yahoo.com", "goState");
        Users u3 = new Users("Jack", "Purdy", "jpurdy@fakeEmail,com", "redhouse12");
        Users u4 = new Users("George", "Watt", "george.watt@iastate.edu", "steelers43football");
        Users u5 = new Users("k", "k", "k", "k");

        userRepository.save(u1);
        userRepository.save(u2);
        userRepository.save(u3);
        userRepository.save(u4);
        userRepository.save(u5);

        SkillLevel sk1 = new SkillLevel(9, u1);
        SkillLevel sk2 = new SkillLevel(8, u1);
        SkillLevel sk3 = new SkillLevel(7, u2);
        SkillLevel sk4 = new SkillLevel(3, u3);
        SkillLevel sk5 = new SkillLevel(10, u3);
        SkillLevel sk6 = new SkillLevel(3, u3);
        SkillLevel sk7 = new SkillLevel(8, u3);

        skillLevelRepository.save(sk1);
        skillLevelRepository.save(sk2);
        skillLevelRepository.save(sk3);
        skillLevelRepository.save(sk4);
        skillLevelRepository.save(sk5);
        skillLevelRepository.save(sk6);
        skillLevelRepository.save(sk7);

        Sport s1 = new Sport("Basketball");
        Sport s2 = new Sport("Pickleball");
        Sport s3 = new Sport("Football");
        Sport s4 = new Sport("Spikeball");
        Sport s5 = new Sport("Tennis");
        Sport s6 = new Sport("Volleyball");
        Sport s7 = new Sport("Softball");
        Sport s8 = new Sport("Hockey");

        sportRepository.save(s1);
        sportRepository.save(s2);
        sportRepository.save(s3);
        sportRepository.save(s4);
        sportRepository.save(s5);
        sportRepository.save(s6);
        sportRepository.save(s7);
        sportRepository.save(s8);

        sk1.setSport(s1);
        sk2.setSport(s2);
        sk3.setSport(s3);
        sk4.setSport(s4);

        s1.setSkillLevel(sk1);
        s2.setSkillLevel(sk2);
        s3.setSkillLevel(sk3);
        s4.setSkillLevel(sk4);

        skillLevelRepository.save(sk1);
        skillLevelRepository.save(sk2);
        skillLevelRepository.save(sk3);
        skillLevelRepository.save(sk4);

        sportRepository.save(s1);
        sportRepository.save(s2);
        sportRepository.save(s3);
        sportRepository.save(s4);

        GamePost gp1 = new GamePost(s1, userRepository.findById(3), 24, 20, "1/12/2023",
                "29/09/1988", false);
        GamePost gp2 =
                new GamePost(s2, userRepository.findById(1), 16, 2, "1/2/2023", "1/02/2023", false);
        GamePost gp3 = new GamePost(s3, userRepository.findById(1), 10, 8, "10/08/2023",
                "10/25/2023", false);
        GamePost gp4 = new GamePost(s4, userRepository.findById(4), 6, 3, "11/1/2023", "11/09/2023",
                false);

        gp1.setSport(s1);
        gp2.setSport(s2);
        gp3.setSport(s3);
        gp4.setSport(s4);

        Set<GamePost> gamePostSet1 = new HashSet<>();
        gamePostSet1.add(gp1);
        s1.setGamePosts(gamePostSet1);
        Set<GamePost> gamePostSet2 = new HashSet<>();
        gamePostSet2.add(gp2);
        s1.setGamePosts(gamePostSet2);
        Set<GamePost> gamePostSet3 = new HashSet<>();
        gamePostSet3.add(gp3);
        s1.setGamePosts(gamePostSet3);
        Set<GamePost> gamePostSet4 = new HashSet<>();
        gamePostSet4.add(gp4);
        s1.setGamePosts(gamePostSet4);

        s1.getGamePosts().add(gp1);
        s2.getGamePosts().add(gp2);
        s3.getGamePosts().add(gp3);
        s4.getGamePosts().add(gp4);

        gamePostRepository.save(gp1);
        gamePostRepository.save(gp2);
        gamePostRepository.save(gp3);
        gamePostRepository.save(gp4);

        gp1.setPlayersSignedUp(gp1.getCreated_by());
        gp1.setPlayersSignedUp(u2);
        gp1.setLocation(new Location("28 South Duff ave", "34, 23", gp1));
        gp2.setPlayersSignedUp(gp2.getCreated_by());
        gp2.setLocation(new Location("4730 Beach Road", "756, 21", gp2));
        gp3.setPlayersSignedUp(gp3.getCreated_by());
        gp3.setLocation(new Location("PGA Drive", "309, 24", gp3));
        gp4.setPlayersSignedUp(gp4.getCreated_by());
        gp4.setLocation(new Location("New Orleans road", "894, 124", gp4));

        Comment c1 = new Comment(gp2, u1);
        Comment c2 = new Comment(gp2, u2);
        Comment c3 = new Comment(gp3, u3);
        Comment c4 = new Comment(gp1, u1);

        commentRepository.save(c1);
        commentRepository.save(c2);
        commentRepository.save(c3);
        commentRepository.save(c4);

        CommentMessage cm1 = new CommentMessage("This event will be held inside", c1);
        CommentMessage cm2 = new CommentMessage("I will be there", c2);
        CommentMessage cm3 = new CommentMessage("This is a third comment", c3);
        CommentMessage cm4 = new CommentMessage("This is a fourth comment", c4);

        commentMessageRepository.save(cm1);
        commentMessageRepository.save(cm2);
        commentMessageRepository.save(cm3);
        commentMessageRepository.save(cm4);

        logger.info("created dummy data");
        return "Successfully created dummy data";
    }

    /**********************************************************************************************/
    /**
     * @fn @QueryMapping public List<Users> getAllUsers()
     *
     * @brief Gets all users
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @returns all users.
     **************************************************************************************************/

    @QueryMapping
    public List<Users> getAllUsers() {
        System.out.println("Got a request");

        logger.info("getAllUsers request");
        return userRepository.findAll();
    }

    /**********************************************************************************************/
    /**
     * @fn @QueryMapping public Users getUserById(@Argument int id)
     *
     * @brief Gets user by identifier
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @exception NotFoundException Thrown when the requested element is not present.
     *
     * @param id The identifier.
     *
     * @returns The user by identifier.
     **************************************************************************************************/

    @QueryMapping
    public Users getUserById(@Argument int id) {
        System.out.println("Got a request");

        Users user = userRepository.findById((id));
        if (user == null) {
            logger.debug("User with ID " + id + " not found");
            throw new NotFoundException("User with ID " + id + " not found");
        }
        return user;
    }

    /**********************************************************************************************/
    /**
     * @fn @QueryMapping public Users getUserByEmail(@Argument String email)
     *
     * @brief Gets user by email
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @exception Exception Thrown when an exception error condition occurs.
     *
     * @param email The email.
     *
     * @returns The user by email.
     **************************************************************************************************/

    @QueryMapping
    public Users getUserByEmail(@Argument String email) {

        System.out.println("Got a request");
        Users user = null;

        try {
            user = userRepository.findByEmail(email);

        } catch (Exception e) {
            // TODO: handle exception
            logger.error(email, e);
            throw e;
        }

        return user;
    }

    /**********************************************************************************************/
    /**
     * @fn @QueryMapping public permission getUserRole(@Argument String email, @Argument String
     *     password)
     *
     * @brief Gets user role
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @exception Exception Thrown when an exception error condition occurs.
     *
     * @param email The email.
     * @param password The password.
     *
     * @returns The user role.
     **************************************************************************************************/

    @QueryMapping
    public permission getUserRole(@Argument String email, @Argument String password) {
        System.out.println("Got a userRole request");

        permission userRole = null;

        try {
            Users user = userRepository.findByEmail(email);

            if (Auth.isAuthenticated(user, email, password)) {
                userRole = user.getRole();
            }

        } catch (Exception e) {
            // TODO: handle exception
            logger.error(e.getMessage(), e);
            throw e;
        }

        return userRole;
    }

    /**********************************************************************************************/
    /**
     * @property @Argument String email, @Argument String password)
     *
     * @brief Gets the password)
     *
     * @exception DuplicateException Thrown when a Duplicate error condition occurs.
     * @exception Exception Thrown when an exception error condition occurs.
     *
     * @returns The password)
     **************************************************************************************************/
    @MutationMapping
    public Users createUser(@Argument String first_name, @Argument String last_name,
            @Argument String email, @Argument String password) {

        Users user = null;

        try {
            if (doesEmailExist(email)) {
                throw new DuplicateException("A user with this email already exists");
            } else {
                user = new Users(first_name, last_name, email, password);
                userRepository.save(user);

                // send welcome emailemail
                EmailSender.SendEmail(user, "New User Welcome Message", String.format(
                        "Hello, %s!\nWelcome to SportSphere! We are so glad you joined us.\n\nThe SportSphere team",
                        user.getFirst_name()));
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
            logger.error(e.getMessage(), e);
            throw e;
        }

        // user.doOnNext(usersPublisher::publish).subscribe();
        usersPublisher.publish(user);
        return user;
    }

    /**********************************************************************************************/
    /**
     * @property @Argument String last_name, @Argument String email, @Argument String password)
     *
     * @brief Gets the password)
     *
     * @exception Exception Thrown when an exception error condition occurs.
     *
     * @returns The password)
     **************************************************************************************************/
    @MutationMapping
    public Users updateUser(@Argument int id, @Argument String first_name,
            @Argument String last_name, @Argument String email, @Argument String password) {
        Users user = null;
        Users result = null;
        try {
            user = userRepository.findById(id);
            result = user;

            // only authenticated users are permitted to make changes
            if (Auth.isAuthenticated(user, email, password)) {
                if (first_name != null) {
                    user.setFirst_name(first_name);
                }
                if (last_name != null) {
                    user.setLast_name((last_name));
                }
                if (email != null) {
                    user.setEmail(email);
                }
                if (password != null) {
                    user.setPassword(password);
                }

                result = userRepository.save(user);
            }
        } catch (GraphQLException graphQLException) {
            logger.error(graphQLException.getMessage(), graphQLException);
            throw graphQLException;
        } catch (Exception e) {
            // TODO: handle exception
            logger.error(e.getMessage(), e);
            throw e;
        }

        return result;
    }

    /**********************************************************************************************/
    /**
     * @property @Argument String newPassword)
     *
     * @brief Gets the new password)
     *
     * @returns The new password)
     **************************************************************************************************/
    @MutationMapping
    public Users updatePassword(@Argument String email, @Argument String oldPassword,
            @Argument String newPassword) {
        Users _user = userRepository.findByEmail(email);

        if (Auth.isAuthenticated(_user, email, oldPassword)) {
            _user.setPassword(newPassword);
            userRepository.save(_user);
        }

        return _user;
    }

    /**********************************************************************************************/
    /**
     * @property @Argument String email, @Argument String newPassword)
     *
     * @brief Gets the new password)
     *
     * @returns The new password)
     **************************************************************************************************/
    @MutationMapping
    public Users updatePasswordByAdmin(@Argument String adminEmail, @Argument String adminPassword,
            @Argument String email, @Argument String newPassword) {

        Users _user = userRepository.findByEmail(email);
        Users admin = userRepository.findByEmail(adminEmail);

        if (Auth.isAuthenticated(admin, adminEmail, adminPassword)) {
            _user.setPassword(newPassword);
        }

        return _user;
    }


    /**********************************************************************************************/
    /**
     * @fn @MutationMapping public Users deleteUser(@Argument int id, @Argument String
     *     email, @Argument String password)
     *
     * @brief Deletes the user
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @exception NotFoundException Thrown when the requested element is not present.
     * @exception Exception Thrown when an exception error condition occurs.
     *
     * @param id The identifier.
     * @param email The email.
     * @param password The password.
     *
     * @returns The Users.
     **************************************************************************************************/

    @MutationMapping
    public Users deleteUser(@Argument int id, @Argument String email, @Argument String password) {
        Users user = null;

        try {
            // only authenticated users are permitted to make changes

            user = userRepository.findById(id);
            if (user == null) {
                throw new NotFoundException("User with ID" + id + "not found.");
            }

            if (Auth.isAuthenticated(user, email, password)) {
                userRepository.deleteById(id);
                user = null;
            }

        } catch (Exception e) {
            // TODO: handle exception
            logger.error(e.getMessage(), e);
            throw e;
        }

        return user;
    }

    @MutationMapping
    public Users deleteUserByEmail(@Argument String email, @Argument String password) {
        Users user = null;

        try {
            // only authenticated users are permitted to make changes

            user = userRepository.findByEmail(email);
            if (user == null) {
                throw new NotFoundException("User with ID" + email + "not found.");
            }

            if (Auth.isAuthenticated(user, email, password)) {
                userRepository.deleteById(user.getId());
                user = null;
            }

        } catch (Exception e) {
            // TODO: handle exception
            logger.error(e.getMessage(), e);
            throw e;
        }

        return user;
    }

    /**********************************************************************************************/
    /**
     * @property @Argument permission newRole)
     *
     * @brief Gets the new role)
     *
     * @returns The new role)
     **************************************************************************************************/
    @MutationMapping
    public Users updateRole(@Argument String email, @Argument String password,
            @Argument permission newRole) {
        Users user = userRepository.findByEmail(email);

        if (Auth.isAuthenticated(user, email, password) || Auth.isAdmin(user)) {
            user.setRole(newRole);
            userRepository.save(user);
        }

        return user;
    }

    @MutationMapping
    public Users addUserPreference(@Argument int userId, @Argument String email,
            @Argument String password, @Argument String preferenceValue) {
        Users user = userRepository.findById(userId);
        // UserPreference userPreference = userPreferenceReprository.findByUser(user);


        try {
            if (Auth.isAuthenticated(user, email, password) || Auth.isAdmin(user)) {

                // userPreference.setPreferenceValue(new PreferenceValue(preferenceValue));
                // userPreferenceReprository.save(userPreference);
                // userRepository.save(user);
            }

        } catch (Exception e) {
            // TODO: handle exception
            logger.error(e.getMessage(), e);
            throw e;
        }

        return user;
    }

    /**********************************************************************************************/
    /**
     * @fn @SubscriptionMapping public Flux<String> greetings()
     *
     * @brief Gets the greetings
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @returns A Flux&lt;String&gt;
     **************************************************************************************************/

    @SubscriptionMapping
    public Flux<String> greetings() {

        // Users user = userRepository.findById((id));
        // return user.getUserStream();
        System.out.println("\n\nsubscribed to greetings\n\n");
        logger.info("subscribed to greetings");
        return Mono.delay(Duration.ofMillis(200))
                .flatMapMany(aLong -> Flux.just("Hi!", "Bonjour!", "Hola!", "Ciao!", "Zdravo!"));
    }

    /**********************************************************************************************/
    /**
     * @fn @SubscriptionMapping public Publisher<Users> greetings2()
     *
     * @brief Greetings 2
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @returns A Publisher&lt;Users&gt;
     **************************************************************************************************/

    @SubscriptionMapping
    public Publisher<Users> subUsers() {

        logger.info("subscribed to users");
        return usersPublisher.getUsers();
    }

    /**********************************************************************************************/
    /**
     * @fn public boolean doesEmailExist(String email)
     *
     * @brief Query if 'email' does email exist
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @param email The email.
     *
     * @returns True if it succeeds, false if it fails.
     **************************************************************************************************/

    public boolean doesEmailExist(String email) {
        Users users = userRepository.findByEmail(email);
        return users != null;
    }

    /**********************************************************************************************/
    /**
     * @fn @GraphQlExceptionHandler public GraphQLError handle(BindException ex)
     *
     * @brief Handles
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @param ex Details of the exception.
     *
     * @returns A GraphQLError.
     **************************************************************************************************/

    @GraphQlExceptionHandler
    public GraphQLError handle(BindException ex) {
        logger.error(ex.getMessage(), ex);
        return GraphQLError.newError().errorType(ErrorType.BAD_REQUEST).message("...").build();
    }
}
