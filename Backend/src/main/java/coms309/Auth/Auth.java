

/** @brief	The coms 309. authentication */
package coms309.Auth;

/** @brief	The coms 309. users. users */
import coms309.Users.Users;
/** @brief	The org.springframework.stereotype. service */
import org.springframework.stereotype.Service;
/** @brief	The coms 309. enums. permission.permission */
import coms309.Enums.Permission.permission;

/**********************************************************************************************//**
 * @class	Auth
 *
 * @brief	Class to simulate an authentication process.
 *
 * @author	Arie
 * @date	10/20/2023
 **************************************************************************************************/

@Service
public final class Auth {

    /**********************************************************************************************//**
     * @fn	private Auth()
     *
     * @brief	Constructor that prevents a default instance of this class from being created
     *
     * @author	Arie
     * @date	10/20/2023
     **************************************************************************************************/

    private Auth() {}

    /**********************************************************************************************//**
     * @fn	public static boolean isAuthenticated(Users user, String email, String password)
     *
     * @brief	Query if 'user' is authenticated
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	user		.
     * @param 	email   	.
     * @param 	password	.
     *
     * @returns	boolean: authenticated.
     **************************************************************************************************/

    public static boolean isAuthenticated(Users user, String email, String password) {
        boolean authenticated = false;

        // Admin is always authenticated
        if (user != null && (user.getEmail().toLowerCase().equals(email.toLowerCase())
                && user.getPassword().equals(password)) || isAdmin(user)) {
            authenticated = true;
        }

        return authenticated;
    }

    /**********************************************************************************************//**
     * @fn	public static boolean isAdmin(Users user)
     *
     * @brief	Query if 'user' is admin
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	user	.
     *
     * @returns	boolean: authenticated.
     **************************************************************************************************/

    public static boolean isAdmin(Users user) {
        boolean admin = false;

        if (user != null && user.getRole() == permission.ADMIN) {
            admin = true;
        }

        return admin;
    }
}
