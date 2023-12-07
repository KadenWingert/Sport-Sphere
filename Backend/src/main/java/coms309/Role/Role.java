

/** @brief	The coms 309. role */
package coms309.Role;

/** @brief	The jakarta.persistence. entity */
import jakarta.persistence.Entity;
/** @brief	The jakarta.persistence. generated value */
import jakarta.persistence.GeneratedValue;
/** @brief	Type of the jakarta.persistence. generation */
import jakarta.persistence.GenerationType;
/** @brief	The jakarta.persistence. generated value */
import jakarta.persistence.GeneratedValue;
/** @brief	Type of the jakarta.persistence. generation */
import jakarta.persistence.GenerationType;
/** @brief	Identifier for the jakarta.persistence. */
import jakarta.persistence.Id;
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
 * @class	Role
 *
 * @brief	A role.
 *
 * @author	Arie
 * @date	10/20/2023
 **************************************************************************************************/

@Entity
@Getter @Setter @NoArgsConstructor
public class Role {

    /**********************************************************************************************//**
     * @fn	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) @NotFound(action = NotFoundAction.IGNORE) private int id;
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
    @NotFound(action = NotFoundAction.IGNORE)
    private int id;
    /** @brief	/** @brief	Identifier for the user */
    private int userId;
    /** @brief	/** @brief	Identifier for the permission */
    private int permissionId;        
}