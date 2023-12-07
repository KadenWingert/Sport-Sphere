

/** @brief	The coms 309. sport */
package coms309.Sport;

/** @brief	The coms 309. exceptions. not found exception */
import coms309.Exceptions.NotFoundException;
/** @brief	The coms 309. game post. game post */
import coms309.GamePost.GamePost;
/** @brief	The coms 309. game post. game post repository */
import coms309.GamePost.GamePostRepository;
/** @brief	The coms 309. skill level. skill level */
import coms309.SkillLevel.SkillLevel;
/** @brief	The coms 309. skill level. skill level repository */
import coms309.SkillLevel.SkillLevelRepository;
/** @brief	The coms 309. users. user repository */
import coms309.Users.UserRepository;
/** @brief	The coms 309. users. users */
import coms309.Users.Users;
/** @brief	The jakarta.transaction. transactional */
import jakarta.transaction.Transactional;
/** @brief	The org.checkerframework.checker.units.qual. a */
import org.checkerframework.checker.units.qual.A;
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

/** @brief	Set the java.util. hash belongs to */
import java.util.HashSet;
/** @brief	List of java.util.s */
import java.util.List;
/** @brief	Set the java.util. belongs to */
import java.util.Set;

/**********************************************************************************************//**
 * @class	SportController
 *
 * @brief	A controller for handling sports.
 *
 * @author	Arie
 * @date	10/20/2023
 **************************************************************************************************/

@Controller
public class SportController {

    /**********************************************************************************************//**
     * @property	@Autowired SportRepository sportRepository
     *
     * @brief	Gets the sport repository
     *
     * @returns	The sport repository.
     **************************************************************************************************/

    @Autowired
    SportRepository sportRepository;

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
     * @property	@Autowired SkillLevelRepository skillLevelRepository
     *
     * @brief	Gets the skill level repository
     *
     * @returns	The skill level repository.
     **************************************************************************************************/

    @Autowired
    SkillLevelRepository skillLevelRepository;

    /**********************************************************************************************//**
     * @property	@Autowired UserRepository userRepository
     *
     * @brief	Gets the user repository
     *
     * @returns	The user repository.
     **************************************************************************************************/

    @Autowired
    UserRepository userRepository;

    /**********************************************************************************************//**
     * @fn	@QueryMapping public List<Sport> getAllSports()
     *
     * @brief	Gets all sports
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @returns	List&lt;Sport&gt;
     **************************************************************************************************/

    @QueryMapping
    public List<Sport> getAllSports() {
        return sportRepository.findAll();
    }

    /**********************************************************************************************//**
     * @fn	@QueryMapping public Sport getSportById(@Argument int id)
     *
     * @brief	Gets sport by identifier
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @exception	NotFoundException	Thrown when the requested element is not present.
     *
     * @param 	id	.
     *
     * @returns	Sport.
     **************************************************************************************************/

    @QueryMapping
    public Sport getSportById(@Argument int id) {
        Sport sport = sportRepository.findById((id));
        if (sport == null) {
            throw new NotFoundException("Sport with ID " + id + " not found");
        }
        return sport;
    }

    /**********************************************************************************************//**
     * @fn	@Transactional @QueryMapping public Set<GamePost> getAllGamePostsPlayingASport(@Argument int sport_id)
     *
     * @brief	Gets all game posts playing a sport
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @exception	NotFoundException	Thrown when the requested element is not present.
     *
     * @param 	sport_id	Identifier for the sport.
     *
     * @returns	all game posts playing a sport.
     **************************************************************************************************/

    @Transactional//This gets rid of the following error:failed to lazily initialize a collection of role: coms309.Sport.Sport.gamePosts: could not initialize proxy - no Session
    @QueryMapping
    public Set<GamePost> getAllGamePostsPlayingASport(@Argument int sport_id){
        Sport sport = sportRepository.findById(sport_id);
        if(sport == null){
            throw new NotFoundException("Sport with ID " + sport + " not found");
        }
        Set<GamePost> gamePosts = new HashSet<>();
        for(GamePost gp : sport.getGamePosts()){
            if(gp.getSport().getSport_name().equals(sport.getSport_name())){
                gamePosts.add(gp);
            }
        }
       return gamePosts;
    }

    /**********************************************************************************************//**
     * @fn	@MutationMapping public Sport createSport(@Argument String sport_name, @Argument int skill_level_id)
     *
     * @brief	Creates a sport
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @exception	NotFoundException	Thrown when the requested element is not present.
     *
     * @param 	sport_name	  	Name of the sport.
     * @param 	skill_level_id	Identifier for the skill level.
     *
     * @returns	The new sport.
     **************************************************************************************************/

    @MutationMapping
    public Sport createSport(@Argument String sport_name, @Argument int skill_level_id){
        SkillLevel skillLevel = skillLevelRepository.findById(skill_level_id);
        Sport sport;
        if(skillLevel == null){
            sport = new Sport(sport_name, null);
        }
        else {
            sport = new Sport(sport_name, skillLevel);
        }
        sportRepository.save(sport);
        return sport;
    }

    /**********************************************************************************************//**
     * @fn	@MutationMapping public Sport updateSport(@Argument String sport_name, @Argument int sport_id, @Argument int skill_level_id)
     *
     * @brief	Updates the sport
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @exception	NotFoundException	Thrown when the requested element is not present.
     *
     * @param 	sport_name	  	Name of the sport.
     * @param 	sport_id	  	Identifier for the sport.
     * @param 	skill_level_id	Identifier for the skill level.
     *
     * @returns	A Sport.
     **************************************************************************************************/

    @MutationMapping
    public Sport updateSport(@Argument String sport_name, @Argument int sport_id, @Argument int skill_level_id){
        Sport sport = sportRepository.findById(sport_id);
        if(sport == null){
            throw new NotFoundException("Sport with ID " + sport_id + " not found.");
        }
        SkillLevel skillLevel = skillLevelRepository.findById(skill_level_id);
        if(skillLevel == null){
            throw new NotFoundException("Skill_level with ID " + skill_level_id + " not found.");
        }
        sport.setSkillLevel(skillLevel);
        if(sport_name != null){
            sport.setSport_name(sport_name);
        }
        sportRepository.save(sport);
        return sport;
    }

    /**********************************************************************************************//**
     * @fn	@MutationMapping public String deleteSportById(@Argument int sport_id)
     *
     * @brief	Deletes the sport by identifier described by sport_id
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @exception	NotFoundException	Thrown when the requested element is not present.
     *
     * @param 	sport_id	Identifier for the sport.
     *
     * @returns	A String.
     **************************************************************************************************/

    @MutationMapping
    public String deleteSportById(@Argument int sport_id) {
        Sport sport = sportRepository.findById(sport_id);
        if(sport == null){
            throw new NotFoundException("Sport with ID" + sport_id + " not found.");
        }
        deleteSport(sport);
        sportRepository.deleteById(sport_id);
        return "Sport with id " + sport_id + " has been deleted";
    }

    /**********************************************************************************************//**
     * @fn	@MutationMapping public String deleteAllSports()
     *
     * @brief	Deletes all sports
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @returns	A String.
     **************************************************************************************************/

    @MutationMapping
    public String deleteAllSports() {
        for(Sport s : sportRepository.findAll()) {
            deleteSport(s);
        }
        sportRepository.deleteAll();
        return "All sports have been deleted";
    }

    /**********************************************************************************************//**
     * @fn	private void deleteSport(Sport s)
     *
     * @brief	Helper method used in deleting an individual sport
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	s	A Sport to process.
     **************************************************************************************************/

    private void deleteSport(Sport s) {
        SkillLevel skillLevel = s.getSkillLevel();
        if (skillLevel != null) {
            skillLevel.setSport(null);
            skillLevelRepository.save(skillLevel);
        }

        Set<GamePost> gamePost = s.getGamePosts();
        if (gamePost != null) {
            for (GamePost gp : gamePost) {
                gp.setSport(null);
                gamePostRepository.save(gp);
            }
        }
    }
}
