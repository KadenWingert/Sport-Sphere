

/** @brief	The coms 309. location */
package coms309.UserAddress;

/** @brief	The coms 309. exceptions. duplicate exception */

import coms309.Exceptions.DuplicateException;
import coms309.Exceptions.NotFoundException;
import coms309.Users.UserRepository;
import coms309.Users.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**********************************************************************************************//**
 * @class	LocationController
 *
 * @brief	A controller for handling locations.
 *
 * @author	Arie
 * @date	10/20/2023
 **************************************************************************************************/

@Controller
public class UserAddressController {

    @Autowired
    UserRepository userRepository;


    @Autowired
    UserAddressRepository userAddressRepository;


    @QueryMapping
    public List<UserAddress> getAllUserAddresses() {
        return userAddressRepository.findAll();
    }


    @QueryMapping
   public UserAddress getuserAddressByID(@Argument int id){
        UserAddress userAddress = userAddressRepository.findById((id));
        if(userAddress == null){
            throw new NotFoundException("User Address with ID " + id + " not found");
        }
        return userAddress;
    }

    @MutationMapping
    public UserAddress createUserAddress(@Argument int userID, @Argument String address, @Argument String gps){
        Users user = userRepository.findById(userID);
        if(user == null){
            throw new NotFoundException("User with id: " + userID + " not found");
        }
        if(user.getUserAddress() != null){
            throw new DuplicateException("user with id: " + userID + " Already has an address. Do you want to update the location instead?");
        }
        UserAddress userAddress = new UserAddress(user, address,gps);
       user.setUserAddress(userAddress);
       userAddressRepository.save(userAddress);
        return userAddress;
    }

    @MutationMapping
    public UserAddress updateUserAddress(@Argument int userAddressID, @Argument String address, @Argument String GPS){
        UserAddress userAddress = userAddressRepository.findById(userAddressID);
        if(userAddress == null)
            throw new NotFoundException("userAddressID with id: " + userAddressID + " not found");

        if(address != null)
            userAddress.setAddress(address);

        if(GPS != null)
            userAddress.setGps(GPS);
        userAddressRepository.save(userAddress);
        return userAddress;
    }

    /**********************************************************************************************//**
     * @fn	@MutationMapping public Location deleteLocation(@Argument int id)
     *
     * @brief	Deletes the location described by ID
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @exception	NotFoundException	Thrown when the requested element is not present.
     *
     * @param 	id	The identifier.
     *
     * @returns	A Location.
     **************************************************************************************************/

    @MutationMapping
    public UserAddress deleteUserAddress(@Argument int id) {
        UserAddress userAddress = userAddressRepository.findById(id);
        if (userAddress == null) {
            throw new NotFoundException("userAddress with ID" + id + "not found.");
        }
        Users user = userRepository.findById(userAddress.getUser().getId());
       user.setUserAddress(null);
        userRepository.save(user);
        userAddressRepository.deleteById(id);
        return userAddress;
    }
}
