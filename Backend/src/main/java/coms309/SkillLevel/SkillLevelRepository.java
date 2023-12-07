

/** @brief	The coms 309. skill level */
package coms309.SkillLevel;

/** @brief	The org.springframework.data.jpa.repository. jpa repository */
import org.springframework.data.jpa.repository.JpaRepository;
/** @brief	The org.springframework.stereotype. repository */
import org.springframework.stereotype.Repository;

/**********************************************************************************************//**
 * @interface	SkillLevelRepository
 *
 * @brief	Interface for skill level repository.
 *
 * @author	Arie
 * @date	10/20/2023
 **************************************************************************************************/

@Repository
public interface SkillLevelRepository extends JpaRepository<SkillLevel, Integer> {

    /**********************************************************************************************//**
     * @fn	SkillLevel findById(int id);
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

    SkillLevel findById(int id);

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
