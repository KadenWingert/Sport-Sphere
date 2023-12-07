

/** @brief	The coms 309. location */
package coms309.Location;

/** @brief	The jakarta.persistence. entity */
import jakarta.persistence.Entity;
/** @brief	The jakarta.persistence. generated value */
import jakarta.persistence.GeneratedValue;
/** @brief	Type of the jakarta.persistence. generation */
import jakarta.persistence.GenerationType;
/** @brief	Identifier for the jakarta.persistence. */
import jakarta.persistence.Id;
/** @brief	The com.fasterxml.jackson.annotation. JSON ignore */
import com.fasterxml.jackson.annotation.JsonIgnore;
/** @brief	The coms 309. game post. game post */
import coms309.GamePost.GamePost;
/** @brief	The jakarta.persistence.* */
import jakarta.persistence.*;
/** @brief	(Immutable) the jakarta.validation.constraints. not null */
import jakarta.validation.constraints.NotNull;
/** @brief	The lombok. getter */
import lombok.Getter;
/** @brief	The lombok. no arguments constructor */
import lombok.NoArgsConstructor;
/** @brief	The lombok. setter */
import lombok.Setter;
/** @brief	The org.hibernate.annotations. not found */
import org.hibernate.annotations.NotFound;
/** @brief	The org.hibernate.annotations. not found action */
import org.hibernate.annotations.NotFoundAction;

/**********************************************************************************************//**
 * @class	Location
 *
 * @brief	Location
 *
 * @author	Arie
 * @date	10/20/2023
 **************************************************************************************************/

@Entity @Getter @Setter
@NoArgsConstructor
public class Location {

    /**********************************************************************************************//**
     * @fn	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id") private int id;
     *
     * @brief	Constructor
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	parameter1	(Optional) The first parameter.
     **************************************************************************************************/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**********************************************************************************************//**
     * @fn	@Column(name = "address") private String address;
     *
     * @brief	Constructor
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	parameter1	(Optional) The first parameter.
     **************************************************************************************************/

    @Column(name = "address")
    private String address;

    /**********************************************************************************************//**
     * @fn	@Column(name = "gps") private String gps;
     *
     * @brief	Constructor
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	parameter1	(Optional) The first parameter.
     **************************************************************************************************/

    @Column(name = "gps")
    private String gps;

    /**********************************************************************************************//**
     * @property	@OneToOne @JsonIgnore private GamePost gamePost
     *
     * @brief	Gets the game post
     *
     * @returns	The game post.
     **************************************************************************************************/

    @OneToOne @JsonIgnore
    private GamePost gamePost;

    /**********************************************************************************************//**
     * @fn	public Location(String address, String gps, GamePost gamePost)
     *
     * @brief	Constructor
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	address 	The address.
     * @param 	gps			The GPS.
     * @param 	gamePost	The game post.
     **************************************************************************************************/

    public Location(String address, String gps, GamePost gamePost) {
        this.address = address;
        this.gps = gps;
        this.gamePost = gamePost;
    }

    /**********************************************************************************************//**
     * @fn	public Location(String address, String gps)
     *
     * @brief	Constructor
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	address	The address.
     * @param 	gps	   	The GPS.
     **************************************************************************************************/

    public Location(String address, String gps) {
        this.address = address;
        this.gps = gps;
    }
}