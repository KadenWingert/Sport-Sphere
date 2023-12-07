package dbRelationshipPractice.GamePost;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dbRelationshipPractice.Users.User;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class GamePost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    private int id;

    @Column(name = "sport_name")
    @NotFound(action = NotFoundAction.IGNORE)
    private String sportName;

    @Column(name = "created_by")
    @NotFound(action = NotFoundAction.IGNORE)
    private int created_by;

    @Column(name = "max_players")
    @NotFound(action = NotFoundAction.IGNORE)
    private int maxPlayers;

    @Column(name = "min_players")
    @NotFound(action = NotFoundAction.IGNORE)
    private int minPlayers;

    @Column(name = "playing_on")
    @NotFound(action = NotFoundAction.IGNORE)
    private String playingOn;

    @Column(name = "created_on")
    @NotFound(action = NotFoundAction.IGNORE)
    private String createdOn;

    @Column(name = "is_deleted")
    @NotFound(action = NotFoundAction.IGNORE)
    private boolean isDeleted;


    @OneToMany
    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    private List<User> playersSignedUp;
    public GamePost(){
    }

    public GamePost(String sportName, int created_by, int maxPlayers, int minPlayers, String playingOn, String createdOn, boolean isDeleted) {
        this.sportName = sportName;
        this.created_by = created_by;
        this.maxPlayers = maxPlayers;
        this.minPlayers = minPlayers;
        this.playingOn = playingOn;
        this.createdOn = createdOn;
        this.isDeleted = isDeleted;
    }

    
    /** 
     * @return int
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public String getPlayingOn() {
        return playingOn;
    }

    public void setPlayingOn(String playingOn) {
        this.playingOn = playingOn;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public List<User> getPlayersSignedUp() {
        return playersSignedUp;
    }

    public void setPlayersSignedUp(User newPlayer) {
        playersSignedUp.add(newPlayer);
    }
}