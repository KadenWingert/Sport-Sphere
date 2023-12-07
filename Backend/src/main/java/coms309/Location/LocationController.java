

/** @brief	The coms 309. location */
package coms309.Location;

/** @brief	The coms 309. exceptions. duplicate exception */
import coms309.Exceptions.DuplicateException;
/** @brief	The coms 309. exceptions. not found exception */
import coms309.Exceptions.NotFoundException;
/** @brief	The coms 309. game post. game post */
import coms309.GamePost.GamePost;
/** @brief	The coms 309. game post. game post repository */
import coms309.GamePost.GamePostRepository;
/** @brief	The org.springframework.beans.factory.annotation. autowired */
import org.springframework.beans.factory.annotation.Autowired;
/** @brief	The org.springframework.graphql.data.method.annotation. argument */
import org.springframework.graphql.data.method.annotation.Argument;
/** @brief	The org.springframework.graphql.data.method.annotation. mutation mapping */
import org.springframework.graphql.data.method.annotation.MutationMapping;
/** @brief	The org.springframework.graphql.data.method.annotation. query mapping */
import org.springframework.graphql.data.method.annotation.QueryMapping;
/** @brief	The org.springframework.stereotype. controller */
import org.springframework.stereotype.Controller;

/** @brief	The java.util.* */
import java.util.*;

/**********************************************************************************************//**
 * @class	LocationController
 *
 * @brief	A controller for handling locations.
 *
 * @author	Arie
 * @date	10/20/2023
 **************************************************************************************************/

@Controller
public class LocationController {

    /**********************************************************************************************//**
     * @property	@Autowired LocationRepository locationRepository
     *
     * @brief	Gets the location repository
     *
     * @returns	The location repository.
     **************************************************************************************************/

    @Autowired
    LocationRepository locationRepository;

    /**********************************************************************************************//**
     * @property	@Autowired GamePostRepository gamePostRepository
     *
     * @brief	Gets the game post repository
     *
     * @returns	The game post repository.
     **************************************************************************************************/

    @Autowired
    GamePostRepository gamePostRepository;

    /**********************************************************************************************//**
     * @fn	@QueryMapping public List<Location> getAllLocations()
     *
     * @brief	Gets all locations
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @returns	List&lt;Location&gt;
     **************************************************************************************************/

    @QueryMapping
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    /**********************************************************************************************//**
     * @fn	@QueryMapping public Location getLocationById(@Argument int id)
     *
     * @brief	Gets location by identifier
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @exception	NotFoundException	Thrown when the requested element is not present.
     *
     * @param 	id	.
     *
     * @returns	Location.
     **************************************************************************************************/

    @QueryMapping
   public Location getLocationById(@Argument int id){
        Location location = locationRepository.findById((id));
        if(location == null){
            throw new NotFoundException("Location with ID " + id + " not found");
        }
        return location;
    }

    /**********************************************************************************************//**
     * @fn	@QueryMapping public Set<GamePost> getGamePostsAtLocation(@Argument String address)
     *
     * @brief	Gets game posts at location
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	address	The address.
     *
     * @returns	The game posts at location.
     **************************************************************************************************/

    @QueryMapping
    public Set<GamePost> getGamePostsAtLocation(@Argument String address) {
        Set<GamePost> gamePostSet = new HashSet<>();

        for (GamePost g : gamePostRepository.findAll()) {
            Location location = g.getLocation();
            if (location != null && location.getAddress().equals(address)) {
                gamePostSet.add(g);
            }
        }

        return gamePostSet;
    }

    /**********************************************************************************************//**
     * @fn	@MutationMapping public Location createLocation(@Argument String address, @Argument String gps, @Argument int gamePostID)
     *
     * @brief	Creates a location
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @exception	NotFoundException 	Thrown when the requested element is not present.
     * @exception	DuplicateException	Thrown when a Duplicate error condition occurs.
     *
     * @param 	address   	The address.
     * @param 	gps		  	The GPS.
     * @param 	gamePostID	Identifier for the game post.
     *
     * @returns	The new location.
     **************************************************************************************************/

    @MutationMapping
    public Location createLocation(@Argument String address, @Argument String gps, @Argument int gamePostID){
        GamePost gamePost = gamePostRepository.findById(gamePostID);
        if(gamePost == null){
            throw new NotFoundException("Game post with id: " + gamePostID + " not found");
        }
        if(gamePost.getLocation() != null){
            throw new DuplicateException("Game post with id: " + gamePostID + " Already has a location. Do you want to update the location instead?");
        }
        Location location = new Location(address,gps,gamePost);
        gamePost.setLocation(location);
        locationRepository.save(location);
        return location;
    }

    /**********************************************************************************************//**
     * @fn	@MutationMapping public Location updateLocation(@Argument int locationID, @Argument String address, @Argument String GPS)
     *
     * @brief	Updates the location
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @exception	NotFoundException	Thrown when the requested element is not present.
     *
     * @param 	locationID	Identifier for the location.
     * @param 	address   	The address.
     * @param 	GPS		  	The GPS.
     *
     * @returns	A Location.
     **************************************************************************************************/

    @MutationMapping
    public Location updateLocation(@Argument int locationID, @Argument String address, @Argument String GPS){
        Location location = locationRepository.findById(locationID);
        if(location == null)
            throw new NotFoundException("Location with id: " + locationID + " not found");

        if(address != null)
            location.setAddress(address);

        if(GPS != null)
            location.setGps(GPS);
    locationRepository.save(location);
        return location;
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
    public Location deleteLocation(@Argument int id) {
        Location location = locationRepository.findById(id);
        if (location == null) {
            throw new NotFoundException("Location with ID" + id + "not found.");
        }
        GamePost gamePost = gamePostRepository.findById(location.getGamePost().getId());
        gamePost.setLocation(null);
        gamePostRepository.save(gamePost);
        locationRepository.deleteById(id);
        return location;
    }
}
