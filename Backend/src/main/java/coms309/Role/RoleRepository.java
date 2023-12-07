

/** @brief	The coms 309. role */
package coms309.Role;

/** @brief	The org.springframework.data.jpa.repository. jpa repository */
import org.springframework.data.jpa.repository.JpaRepository;
/** @brief	The org.springframework.stereotype. repository */
import org.springframework.stereotype.Repository;

/** @brief	The javax.xml.stream.events. comment */
import javax.xml.stream.events.Comment;

/**********************************************************************************************//**
 * @interface	RoleRepository
 *
 * @brief	Interface for role repository.
 *
 * @author	Arie
 * @date	10/20/2023
 **************************************************************************************************/

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    /**********************************************************************************************//**
     * @fn	Role findById(int id);
     *
     * @brief	Searches for the first identifier
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	id	The identifier.
     *
     * @returns	The found identifier.
     **************************************************************************************************/

    Role findById(int id);

    /**********************************************************************************************//**
     * @fn	void deleteById(int id);
     *
     * @brief	Deletes the by identifier described by ID
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	id	The identifier.
     **************************************************************************************************/

    void deleteById(int id);
}
