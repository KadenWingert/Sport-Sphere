

/** @brief The coms 309. game post */
package coms309.GamePost;

/** @brief The com.fasterxml.jackson.annotation. JSON ignore */
import com.fasterxml.jackson.annotation.JsonIgnore;
/** @brief The coms 309. comment. comment */
import coms309.Comment.Comment;
/** @brief The coms 309. location. location */
import coms309.Location.Location;
import coms309.SavedGamePost.SavedGamePost;
/** @brief The coms 309. sport. sport */
import coms309.Sport.Sport;
/** @brief The coms 309. users. users */
import coms309.Users.Users;
/** @brief The lombok. getter */
import lombok.Getter;
/** @brief The lombok. setter */
import lombok.Setter;
/** @brief The org.hibernate.annotations. not found */
import org.hibernate.annotations.NotFound;
/** @brief The org.hibernate.annotations. not found action */
import org.hibernate.annotations.NotFoundAction;

/** @brief The jakarta.persistence.* */
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
/** @brief Set the java.util. hash belongs to */
import java.util.HashSet;

/** @brief Set the java.util. belongs to */
import java.util.Set;

/**********************************************************************************************/
/**
 * @class GamePost
 *
 * @brief A game post.
 *
 * @author Arie
 * @date 10/20/2023
 **************************************************************************************************/

/**********************************************************************************************/
/**
 * @class GamePost
 *
 * @brief A game post.
 *
 * @author Arie
 * @date 10/30/2023
 **************************************************************************************************/

@Entity
@Getter
@Setter
public class GamePost {

    /**********************************************************************************************/
    /**
     * @fn @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @NotFound(action =
     *     NotFoundAction.IGNORE) private int id;
     *
     * @brief -- GETTER --
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param parameter1 (Optional) The first parameter.
     **************************************************************************************************/

    /**********************************************************************************************/
    /**
     * @fn @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @NotFound(action =
     *     NotFoundAction.IGNORE) private int id;
     *
     * @brief Constructor
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @param parameter1 (Optional) The first parameter.
     **************************************************************************************************/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotFound(action = NotFoundAction.IGNORE)
    private int id;

    /**********************************************************************************************/
    /**
     * @fn @Column(name = "max_players") @NotFound(action = NotFoundAction.IGNORE) private int
     *     max_players;
     *
     * @brief Constructor
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param parameter1 (Optional) The first parameter.
     **************************************************************************************************/

    /**********************************************************************************************/
    /**
     * @fn @Column(name = "max_players") @NotFound(action = NotFoundAction.IGNORE) private int
     *     max_players;
     *
     * @brief Constructor
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @param parameter1 (Optional) The first parameter.
     **************************************************************************************************/

    @Column(name = "max_players")
    @NotFound(action = NotFoundAction.IGNORE)
    private int max_players;

    /**********************************************************************************************/
    /**
     * @fn @Column(name = "min_players") @NotFound(action = NotFoundAction.IGNORE) private int
     *     min_players;
     *
     * @brief Constructor
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param parameter1 (Optional) The first parameter.
     **************************************************************************************************/

    /**********************************************************************************************/
    /**
     * @fn @Column(name = "min_players") @NotFound(action = NotFoundAction.IGNORE) private int
     *     min_players;
     *
     * @brief Constructor
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @param parameter1 (Optional) The first parameter.
     **************************************************************************************************/

    @Column(name = "min_players")
    @NotFound(action = NotFoundAction.IGNORE)
    private int min_players;

    /**********************************************************************************************/
    /**
     * @fn @Column(name = "playing_on") @NotFound(action = NotFoundAction.IGNORE) private String
     *     playing_on;
     *
     * @brief Constructor
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param parameter1 (Optional) The first parameter.
     **************************************************************************************************/

    /**********************************************************************************************/
    /**
     * @fn @Column(name = "playing_on") @NotFound(action = NotFoundAction.IGNORE) private String
     *     playing_on;
     *
     * @brief Constructor
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @param parameter1 (Optional) The first parameter.
     **************************************************************************************************/

    @Column(name = "playing_on")
    @NotFound(action = NotFoundAction.IGNORE)
    private String playing_on;

    /**********************************************************************************************/
    /**
     * @fn @Column(name = "created_on") @NotFound(action = NotFoundAction.IGNORE) private String
     *     created_on;
     *
     * @brief Constructor
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param parameter1 (Optional) The first parameter.
     **************************************************************************************************/

    /**********************************************************************************************/
    /**
     * @fn @Column(name = "created_on") @NotFound(action = NotFoundAction.IGNORE) private String
     *     created_on;
     *
     * @brief Constructor
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @param parameter1 (Optional) The first parameter.
     **************************************************************************************************/

    @Column(name = "created_on")
    @NotFound(action = NotFoundAction.IGNORE)
    private String created_on;

    /**********************************************************************************************/
    /**
     * @fn @Column(name = "is_deleted") @NotFound(action = NotFoundAction.IGNORE) private boolean
     *     is_deleted;
     *
     * @brief Constructor
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param parameter1 (Optional) The first parameter.
     **************************************************************************************************/

    /**********************************************************************************************/
    /**
     * @fn @Column(name = "is_deleted") @NotFound(action = NotFoundAction.IGNORE) private boolean
     *     is_deleted;
     *
     * @brief Constructor
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @param parameter1 (Optional) The first parameter.
     **************************************************************************************************/

    @Column(name = "is_deleted")
    @NotFound(action = NotFoundAction.IGNORE)
    private boolean is_deleted;

    /**********************************************************************************************/
    /**
     * @fn @OneToOne(cascade = CascadeType.ALL) @JoinColumn(name = "location_id") private Location
     *     location;
     *
     * @brief Constructor
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param parameter1 (Optional) The first parameter.
     **************************************************************************************************/

    /**********************************************************************************************/
    /**
     * @fn @OneToOne(cascade = CascadeType.ALL) @JoinColumn(name = "location_id") private Location
     *     location;
     *
     * @brief Constructor
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @param parameter1 (Optional) The first parameter.
     **************************************************************************************************/

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;

    /**********************************************************************************************/
    /**
     * @fn @ManyToOne @JoinColumn(name = "created_by_user_id") private Users created_by;
     *
     * @brief Constructor
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @param parameter1 (Optional) The first parameter.
     **************************************************************************************************/

    @ManyToOne(fetch = FetchType.EAGER) // need to add somthing to make it notnull
    @JoinColumn(name = "created_by_user_id")
    @NotNull
    private Users created_by;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "players_signed_up", joinColumns = @JoinColumn(name = "game_post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnore
    private Set<Users> playersSignedUp = new HashSet<>();


    /**********************************************************************************************/
    /**
     * @fn @OneToMany(mappedBy = "gamePost", cascade = CascadeType.ALL, orphanRemoval = true)
     *     private Set<Comment> comment = new HashSet<>();
     *
     * @brief Constructor
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @param parameter1 (Optional) The first parameter.
     * @param parameter2 (Optional) The second parameter.
     * @param parameter3 (Optional) The third parameter.
     **************************************************************************************************/

    @OneToMany(mappedBy = "gamePost", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comment = new HashSet<>();

    /**********************************************************************************************/
    /**
     * @fn @ManyToOne @JoinColumn(name = "sport_id") private Sport sport;
     *
     * @brief Constructor
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @param parameter1 (Optional) The first parameter.
     **************************************************************************************************/

    @ManyToOne
    @JoinColumn(name = "sport_id") // links the Sport class with the primary key of the GamePost
    private Sport sport;

    // @Null
    // @OneToOne(cascade = CascadeType.ALL)
    // @JoinColumn(name = "savedgame_id", referencedColumnName = "id")
    // private SavedGamePost savedGamePost;

    // @Null
    // @OneToOne(mappedBy = "gamePost", cascade = CascadeType.ALL)
    // @PrimaryKeyJoinColumn
    // private SavedGamePost savedGamePost;

    /**********************************************************************************************
     * /**
     * 
     * @fn public GamePost()
     *
     * @brief Default constructor
     *
     * @author Arie
     * @date 10/30/2023
     **************************************************************************************************/

    public GamePost() {}

    /**********************************************************************************************/
    /**
     * @fn public GamePost(Sport sport, Users created_by, int max_players, int min_players, String
     *     playing_on, String created_on, boolean is_deleted)
     *
     * @brief Constructor
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param sport The sport.
     * @param created_by Amount to created by.
     * @param max_players The maximum players.
     * @param min_players The minimum players.
     * @param playing_on The playing on.
     * @param created_on The created on.
     * @param is_deleted True if is deleted, false if not.
     **************************************************************************************************/

    /************************************************************************************************
     * @property String playing_on, String created_on, boolean is_deleted)
     *
     * @brief Gets a value indicating whether this object is deleted)
     *
     * @returns True if this object is deleted), false if not.
     **************************************************************************************************/
    public GamePost(Sport sport, Users created_by, int max_players, int min_players,
            String playing_on, String created_on, boolean is_deleted) {
        this.sport = sport;
        this.created_by = created_by;
        playersSignedUp.add(created_by);
        this.max_players = max_players;
        this.min_players = min_players;
        this.playing_on = playing_on;
        this.created_on = created_on;
        this.is_deleted = is_deleted;
    }

    /**********************************************************************************************/
    /**
     * @fn public GamePost(Sport sport, Users created_by, int max_players, int min_players, String
     *     playing_on, String created_on, boolean is_deleted, String address, String GPS)
     *
     * @brief Constructor
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param sport The sport.
     * @param created_by Amount to created by.
     * @param max_players The maximum players.
     * @param min_players The minimum players.
     * @param playing_on The playing on.
     * @param created_on The created on.
     * @param is_deleted True if is deleted, false if not.
     * @param address The address.
     * @param GPS The GPS.
     **************************************************************************************************/

    public GamePost(Sport sport, Users created_by, int max_players, int min_players,

            /************************************************************************************************
             * @property String playing_on, String created_on, boolean is_deleted, String address,
             *           String GPS)
             *
             * @brief Gets the gps)
             *
             * @returns The gps)
             **************************************************************************************************/

            String playing_on, String created_on, boolean is_deleted, String address, String GPS) {
        this.sport = sport;
        this.created_by = created_by;
        playersSignedUp.add(created_by);
        this.max_players = max_players;
        this.min_players = min_players;
        this.playing_on = playing_on;
        this.created_on = created_on;
        this.is_deleted = is_deleted;
        this.location = new Location(address, GPS);
    }


    /**********************************************************************************************/
    /**
     * @fn public void setId(int id)
     *
     * @brief Sets an identifier
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @param id The identifier.
     **************************************************************************************************/

    public void setId(int id) {
        this.id = id;
    }


    /**********************************************************************************************/
    /**
     * @fn public void setCreated_by(Users created_by)
     *
     * @brief Sets created by
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @param created_by Amount to created by.
     **************************************************************************************************/

    public void setCreated_by(Users created_by) {
        this.created_by = created_by;
    }


    /**********************************************************************************************/
    /**
     * @fn public void setMax_players(int maxPlayers)
     *
     * @brief Sets maximum players
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @param maxPlayers The maximum players.
     **************************************************************************************************/

    public void setMax_players(int maxPlayers) {
        this.max_players = maxPlayers;
    }


    /**********************************************************************************************/
    /**
     * @fn public void setMin_players(int minPlayers)
     *
     * @brief Sets minimum players
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @param minPlayers The minimum players.
     **************************************************************************************************/

    public void setMin_players(int minPlayers) {
        this.min_players = minPlayers;
    }


    /**********************************************************************************************/
    /**
     * @fn public void setPlaying_on(String playingOn)
     *
     * @brief Sets playing on
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @param playingOn The playing on.
     **************************************************************************************************/

    public void setPlaying_on(String playingOn) {
        this.playing_on = playingOn;
    }


    /**********************************************************************************************/
    /**
     * @fn public void setCreated_on(String createdOn)
     *
     * @brief Sets created on
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @param createdOn The created on.
     **************************************************************************************************/

    public void setCreated_on(String createdOn) {
        this.created_on = createdOn;
    }


    /**********************************************************************************************/
    /**
     * @fn public boolean getIs_deleted()
     *
     * @brief Gets is deleted
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @returns True if it succeeds, false if it fails.
     **************************************************************************************************/

    public boolean getIs_deleted() {
        return is_deleted;
    }


    /**********************************************************************************************/
    /**
     * @fn public void setIs_deleted(boolean is_deleted)
     *
     * @brief Sets is deleted
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @param is_deleted True if is deleted, false if not.
     **************************************************************************************************/

    public void setIs_deleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }


    /**********************************************************************************************/
    /**
     * @fn public void setPlayersSignedUp(Users user)
     *
     * @brief Sets players signed up
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @param user The user.
     **************************************************************************************************/

    public void setPlayersSignedUp(Users user) {
        playersSignedUp.add(user);
    }


    /**********************************************************************************************/
    /**
     * @fn public void setLocation(Location location)
     *
     * @brief Sets a location
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @paramlocation.
     **************************************************************************************************/

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }


    /**********************************************************************************************/
    /**
     * @fn public void setPlayersSignedUp(Set<Users> playersSignedUp)
     *
     * @brief Sets players signed up
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @param playersSignedUp The players signed up.
     **************************************************************************************************/

    public void setPlayersSignedUp(Set<Users> playersSignedUp) {
        this.playersSignedUp = playersSignedUp;
    }


    /**********************************************************************************************/
    /**
     * @fn public void setComment(Set<Comment> comment)
     *
     * @brief Sets a comment
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @param comment The comment.
     **************************************************************************************************/

    public void setComment(Set<Comment> comment) {
        this.comment = comment;
    }


    /**********************************************************************************************/
    /**
     * @fn public void setSport(Sport sport)
     *
     * @brief Sets a sport
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @param sport The sport.
     **************************************************************************************************/

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    /**********************************************************************************************/
    /**
     * @fn public void removePlayerSignedUp(Users user)
     *
     * @brief Removes the player signed up described by user
     *
     * @author Arie
     * @date 10/30/2023
     *
     * @param user The user.
     **************************************************************************************************/

    public void removePlayerSignedUp(Users user) {
        playersSignedUp.removeIf(u -> u.equals(user));
    }
}
