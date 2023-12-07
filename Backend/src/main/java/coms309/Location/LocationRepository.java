

/** @brief	The coms 309. location */
package coms309.Location;

/** @brief	The org.springframework.data.jpa.repository. jpa repository */
import org.springframework.data.jpa.repository.JpaRepository;
/** @brief	The org.springframework.stereotype. repository */
import org.springframework.stereotype.Repository;

/** @brief	The javax.xml.stream.events. comment */
import javax.xml.stream.events.Comment;

/**********************************************************************************************//**
 * @interface	LocationRepository
 *
 * @brief	Interface for location repository.
 *
 * @author	Arie
 * @date	10/20/2023
 **************************************************************************************************/

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

    /**********************************************************************************************//**
     * @fn	Location findById(int id);
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

    Location findById(int id);

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
