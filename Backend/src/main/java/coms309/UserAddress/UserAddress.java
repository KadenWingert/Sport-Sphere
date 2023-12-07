

/** @brief	The coms 309. location */
package coms309.UserAddress;

/** @brief	The jakarta.persistence. entity */

import com.fasterxml.jackson.annotation.JsonIgnore;
import coms309.Users.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/** @brief	The org.hibernate.annotations. not found */
/** @brief	The org.hibernate.annotations. not found action */

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
public class UserAddress {

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
    private Users user;


    public UserAddress(Users user, String address, String gps) {
        this.address = address;
        this.gps = gps;
        this.user = user;
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

    public UserAddress(String address, String gps) {
        this.address = address;
        this.gps = gps;
    }
}