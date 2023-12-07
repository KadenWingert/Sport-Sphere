

/** @brief The coms 309. users */
package coms309.Users;

/** @brief The coms 309. auth. authentication */
import coms309.Auth.Auth;
/** @brief The coms 309. enums. permission.permission */
import coms309.Enums.Permission.permission;
/** @brief The coms 309. game post. game post */
import coms309.GamePost.GamePost;
import coms309.PreferenceValue.PreferenceValue;
import coms309.Profile.Profile;
import coms309.SavedGamePost.SavedGamePost;
/** @brief The coms 309. skill level. skill level */
import coms309.SkillLevel.SkillLevel;
import coms309.UserAddress.UserAddress;
/** @brief The org.hibernate.annotations. not found */
import org.hibernate.annotations.NotFound;
/** @brief The org.hibernate.annotations. not found action */
import org.hibernate.annotations.NotFoundAction;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails.Address;
/** @brief The jakarta.persistence.* */
import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
/** @brief The lombok. getter */
import lombok.Getter;
/** @brief The lombok. setter */
import lombok.Setter;
/** @brief The reactor.core.publisher. flux */
import reactor.core.publisher.Flux;
/** @brief The reactor.core.publisher. mono */
import reactor.core.publisher.Mono;
/** @brief Duration of the java.time. */
import java.time.Duration;
/** @brief Set the java.util. hash belongs to */
import java.util.HashSet;
/** @brief The java.util. objects */
import java.util.Objects;
/** @brief Set the java.util. belongs to */
import java.util.Set;

/**********************************************************************************************/
/**
 * @class Users
 *
 * @brief An users.
 *
 * @author Arie
 * @date 10/20/2023
 **************************************************************************************************/

@Entity
@Getter
@Setter
@Table(name = "users")

public class Users {

    /**********************************************************************************************/
    /**
     * @fn @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @NotFound(action =
     *     NotFoundAction.IGNORE) private int id;
     *
     * @brief Constructor
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param parameter1 (Optional) The first parameter.
     **************************************************************************************************/

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_address")
    private UserAddress userAddress;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotFound(action = NotFoundAction.IGNORE)
    private int id;

    /**********************************************************************************************/
    /**
     * @fn @NotFound(action = NotFoundAction.IGNORE) private String first_name;
     *
     * @brief Constructor
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param parameter1 (Optional) The first parameter.
     **************************************************************************************************/

    @NotFound(action = NotFoundAction.IGNORE)
    private String first_name;

    /**********************************************************************************************/
    /**
     * @fn @NotFound(action = NotFoundAction.IGNORE) private String last_name;
     *
     * @brief Constructor
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param parameter1 (Optional) The first parameter.
     **************************************************************************************************/

    @NotFound(action = NotFoundAction.IGNORE)
    private String last_name;

    /**********************************************************************************************/
    /**
     * @fn @NotFound(action = NotFoundAction.IGNORE) private String email;
     *
     * @brief Constructor
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param parameter1 (Optional) The first parameter.
     **************************************************************************************************/

    @NotFound(action = NotFoundAction.IGNORE)
    private String email;

    /**********************************************************************************************/
    /**
     * @fn @NotFound(action = NotFoundAction.IGNORE) private String password;
     *
     * @brief Constructor
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param parameter1 (Optional) The first parameter.
     **************************************************************************************************/

    @NotFound(action = NotFoundAction.IGNORE)
    private String password;

    /**********************************************************************************************/
    /**
     * @fn @NotFound(action = NotFoundAction.IGNORE) private permission role;
     *
     * @brief Constructor
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param parameter1 (Optional) The first parameter.
     **************************************************************************************************/

    @NotFound(action = NotFoundAction.IGNORE)
    private permission role;

    /**********************************************************************************************/
    /**
     * @fn @ManyToMany(mappedBy = "playersSignedUp") Set<GamePost> gamePost;
     *
     * @brief Constructor
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param parameter1 (Optional) The first parameter.
     **************************************************************************************************/

    @ManyToMany(mappedBy = "playersSignedUp")
    Set<GamePost> gamePost;

    /**********************************************************************************************/
    /**
     * @fn @OneToMany(mappedBy = "created_by", cascade = CascadeType.ALL, orphanRemoval = true)
     *     private Set<GamePost> createdGamePosts;
     *
     * @brief Constructor
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param parameter1 (Optional) The first parameter.
     * @param parameter2 (Optional) The second parameter.
     * @param parameter3 (Optional) The third parameter.
     **************************************************************************************************/

    @OneToMany(mappedBy = "created_by", cascade = CascadeType.ALL, orphanRemoval = true,
            fetch = FetchType.EAGER)
    private Set<GamePost> createdGamePosts;

    /**********************************************************************************************/
    /**
     * @fn @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch =
     *     FetchType.EAGER) private Set<SkillLevel> skill_levels = new HashSet<>();
     *
     * @brief Constructor
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param parameter1 (Optional) The first parameter.
     * @param parameter2 (Optional) The second parameter.
     * @param parameter3 (Optional) The third parameter.
     * @param parameter4 (Optional) The fourth parameter.
     **************************************************************************************************/

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true,
            fetch = FetchType.EAGER)
    private Set<SkillLevel> skill_levels = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "user_preference",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "preference_value_id", referencedColumnName = "id")})
    private PreferenceValue preferenceValue;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<SavedGamePost> savedGamePosts;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Profile profile;

    /**********************************************************************************************
     * 
     * /**
     * 
     * @fn public Users()
     *
     * @brief Default constructor
     *
     * @author Arie
     * @date 10/20/2023
     **************************************************************************************************/

    public Users() {}

    /**********************************************************************************************/
    /**
     * @fn public Users(String firstName, String last_name, String email, String password)
     *
     * @brief Constructor
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param firstName The person's first name.
     * @param last_name The person's last name.
     * @param email The email.
     * @param password The password.
     **************************************************************************************************/

    public Users(String firstName, String last_name, String email, String password) {
        this.first_name = firstName;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.role = permission.USER; // default to user level
        // this.comment = null;
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
     * @returns int.
     **************************************************************************************************/

    public int getId() {
        return id;
    }

    /**********************************************************************************************/
    /**
     * @fn public void setId(int id)
     *
     * @brief Sets an identifier
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param id .
     **************************************************************************************************/

    public void setId(int id) {
        this.id = id;
    }

    /**********************************************************************************************/
    /**
     * @fn public String getFirst_name()
     *
     * @brief Get the first name
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @returns String.
     **************************************************************************************************/

    public String getFirst_name() {
        return first_name;
    }

    /**********************************************************************************************/
    /**
     * @fn public void setFirst_name(String firstName)
     *
     * @brief Set the first name
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param firstName .
     **************************************************************************************************/

    public void setFirst_name(String firstName) {
        this.first_name = firstName;
    }

    /**********************************************************************************************/
    /**
     * @fn public String getLast_name()
     *
     * @brief Get the last name
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @returns String.
     **************************************************************************************************/

    public String getLast_name() {
        return last_name;
    }

    /**********************************************************************************************/
    /**
     * @fn public void setLast_name(String lastName)
     *
     * @brief Set the last name
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param lastName .
     **************************************************************************************************/

    public void setLast_name(String lastName) {
        this.last_name = lastName;
    }

    /**********************************************************************************************/
    /**
     * @fn public String getEmail()
     *
     * @brief Gets the email
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @returns The email.
     **************************************************************************************************/

    public String getEmail() {
        return email;
    }

    /**********************************************************************************************/
    /**
     * @fn public void setEmail(String email)
     *
     * @brief Sets an email
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param email The email.
     **************************************************************************************************/

    public void setEmail(String email) {
        this.email = email;
    }

    /**********************************************************************************************/
    /**
     * @fn public String getPassword()
     *
     * @brief Gets the password
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @returns The password.
     **************************************************************************************************/

    public String getPassword() {
        return password;
    }

    /**********************************************************************************************/
    /**
     * @fn public void setPassword(String password)
     *
     * @brief Sets a password
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param password The password.
     **************************************************************************************************/

    public void setPassword(String password) {
        this.password = password;
    }

    /**********************************************************************************************/
    /**
     * @fn public Set<GamePost> getGamePost()
     *
     * @brief Gets game post
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @returns The game post.
     **************************************************************************************************/

    public Set<GamePost> getGamePost() {
        return gamePost;
    }

    /**********************************************************************************************/
    /**
     * @fn public void setGamePost(Set<GamePost> gamePost)
     *
     * @brief Sets game post
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param gamePost The game post.
     **************************************************************************************************/

    public void setGamePost(Set<GamePost> gamePost) {
        this.gamePost = gamePost;
    }

    /**********************************************************************************************/
    /**
     * @fn public Set<GamePost> getCreatedGamePosts()
     *
     * @brief Gets created game posts
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @returns The created game posts.
     **************************************************************************************************/

    public Set<GamePost> getCreatedGamePosts() {
        return createdGamePosts;
    }

    /**********************************************************************************************/
    /**
     * @fn public void setCreatedGamePosts(Set<GamePost> createdGamePosts)
     *
     * @brief Sets created game posts
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param createdGamePosts The created game posts.
     **************************************************************************************************/

    public void setCreatedGamePosts(Set<GamePost> createdGamePosts) {
        this.createdGamePosts = createdGamePosts;
    }

    /**********************************************************************************************/
    /**
     * @fn public Set<SkillLevel> getSkill_levels()
     *
     * @brief Gets skill levels
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @returns The skill levels.
     **************************************************************************************************/

    public Set<SkillLevel> getSkill_levels() {
        return skill_levels;
    }

    /**********************************************************************************************/
    /**
     * @fn public void setSkill_levels(Set<SkillLevel> sports_skill_levels)
     *
     * @brief Sets skill levels
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param sports_skill_levels The sports skill levels.
     **************************************************************************************************/

    public void setSkill_levels(Set<SkillLevel> sports_skill_levels) {
        this.skill_levels = sports_skill_levels;
    }

    /**********************************************************************************************/
    /**
     * @fn public boolean isAuthenticated(String email, String password)
     *
     * @brief Query if 'email' is authenticated
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param email The email.
     * @param password The password.
     *
     * @returns True if authenticated, false if not.
     **************************************************************************************************/

    public boolean isAuthenticated(String email, String password) {
        return Auth.isAuthenticated(this, email, password);
    }

    /**********************************************************************************************/
    /**
     * @fn public boolean isAdmin(Users u)
     *
     * @brief Query if 'u' is admin
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param u The Users to process.
     *
     * @returns True if admin, false if not.
     **************************************************************************************************/

    public boolean isAdmin(Users u) {
        return Auth.isAdmin(this);
    }

    /**********************************************************************************************/
    /**
     * @fn public permission getRole()
     *
     * @brief Gets the role
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @returns The role.
     **************************************************************************************************/

    public permission getRole() {
        return role;
    }

    /**********************************************************************************************/
    /**
     * @fn public void setRole(permission role)
     *
     * @brief Sets a role
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param role The role.
     **************************************************************************************************/

    public void setRole(permission role) {
        this.role = role;
    }

    /**********************************************************************************************/
    /**
     * @fn @Override public boolean equals(Object o)
     *
     * @brief Tests if this Object is considered equal to another
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param o The object to compare to this object.
     *
     * @returns True if the objects are considered equal, false if they are not.
     **************************************************************************************************/

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Users users = (Users) o;
        return id == users.id && Objects.equals(first_name, users.first_name)
                && Objects.equals(last_name, users.last_name) && Objects.equals(email, users.email)
                && Objects.equals(password, users.password) && role == users.role;
    }

    /**********************************************************************************************/
    /**
     * @fn public Flux<String> getUserStream()
     *
     * @brief Gets user stream
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @returns The user stream.
     **************************************************************************************************/

    public Flux<String> getUserStream() {

        return Mono.delay(Duration.ofMillis(50))
                .flatMapMany(aLong -> Flux.just("Hi!", "Bonjour!", "Hola!", "Ciao!", "Zdravo!"));
    }

    public UserAddress getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(UserAddress userAddress) {
        this.userAddress = userAddress;
    }
}
