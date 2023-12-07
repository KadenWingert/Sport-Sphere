

/** @brief The coms 309. users */
package coms309.Users;


/** @brief The org.springframework.data.jpa.repository. jpa repository */
import org.springframework.data.jpa.repository.JpaRepository;
/** @brief The org.springframework.stereotype. repository */
import org.springframework.stereotype.Repository;

/**********************************************************************************************/
/**
 * @interface UserRepository
 *
 * @brief Interface for user repository.
 *
 * @author Arie
 * @date 10/20/2023
 **************************************************************************************************/

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

    /**********************************************************************************************/
    /**
     * @fn Users findById(int id);
     *
     * @brief Searches for the first identifier
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param id The identifier.
     *
     * @returns The found identifier.
     **************************************************************************************************/

    Users findById(int id);

    /**********************************************************************************************/
    /**
     * @fn Users findByEmail(String email);
     *
     * @brief Searches for the first email
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param email The email.
     *
     * @returns The found email.
     **************************************************************************************************/

    Users findByEmail(String email);

    // Users findByEmailPassword(String email, String password);

    /**********************************************************************************************/
    /**
     * @fn void deleteById(int id);
     *
     * @brief Deletes the by identifier described by ID
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param id The identifier.
     **************************************************************************************************/

    void deleteById(int id);

    /**********************************************************************************************/
    /**
     * @fn Users findByRole(int id);
     *
     * @brief Searches for the first role
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param id The identifier.
     *
     * @returns The found role.
     **************************************************************************************************/

    Users findByRole(int id);
}
