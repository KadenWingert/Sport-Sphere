

/** @brief	The coms 309. comment */
package coms309.Comment;

/** @brief	The org.springframework.data.jpa.repository. jpa repository */
import org.springframework.data.jpa.repository.JpaRepository;
/** @brief	The org.springframework.stereotype. repository */
import org.springframework.stereotype.Repository;

/**********************************************************************************************//**
 * @interface	CommentRepository
 *
 * @brief	Interface for comment repository.
 *
 * @author	Arie
 * @date	10/20/2023
 **************************************************************************************************/

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    /**********************************************************************************************//**
     * @fn	Comment findById(int id);
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

    Comment findById(int id);

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
