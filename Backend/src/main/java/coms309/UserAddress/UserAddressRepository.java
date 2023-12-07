

/** @brief	The coms 309. location */
package coms309.UserAddress;

/** @brief	The org.springframework.data.jpa.repository. jpa repository */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Integer> {

    UserAddress findById(int id);


    void deleteById(int id);
}
