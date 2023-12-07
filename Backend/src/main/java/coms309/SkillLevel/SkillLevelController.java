

/** @brief	The coms 309. skill level */
package coms309.SkillLevel;

/** @brief	The coms 309. exceptions. not found exception */
import coms309.Exceptions.NotFoundException;
/** @brief	The coms 309. game post. game post */
import coms309.GamePost.GamePost;
/** @brief	The coms 309. sport. sport */
import coms309.Sport.Sport;
/** @brief	The coms 309. sport. sport repository */
import coms309.Sport.SportRepository;
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

/** @brief	List of java.util.s */
import java.util.List;
/** @brief	Set the java.util. belongs to */
import java.util.Set;

/**********************************************************************************************//**
 * @class	SkillLevelController
 *
 * @brief	A controller for handling skill levels.
 *
 * @author	Arie
 * @date	10/20/2023
 **************************************************************************************************/

@Controller
public class SkillLevelController {

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
     * @property	@Autowired SportRepository sportRepository
     *
     * @brief	Gets the sport repository
     *
     * @returns	The sport repository.
     **************************************************************************************************/

    @Autowired
    SportRepository sportRepository;

    /**********************************************************************************************//**
     * @fn	@QueryMapping public List<SkillLevel> getAllSkillLevels()
     *
     * @brief	Gets all skill levels
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @returns	List&lt;SkillLevel&gt;
     **************************************************************************************************/

    @QueryMapping
    public List<SkillLevel> getAllSkillLevels() {
        return skillLevelRepository.findAll();
    }

    /**********************************************************************************************//**
     * @fn	@QueryMapping public SkillLevel getSkillLevelById(@Argument int id)
     *
     * @brief	Gets skill level by identifier
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @exception	NotFoundException	Thrown when the requested element is not present.
     *
     * @param 	id	.
     *
     * @returns	SkillLevel.
     **************************************************************************************************/

    @QueryMapping
    public SkillLevel getSkillLevelById(@Argument int id) {
        SkillLevel skillLevel = skillLevelRepository.findById((id));
        if (skillLevel == null) {
            throw new NotFoundException("SkillLevel with ID " + id + " not found");
        }
        return skillLevel;
    }

    /**********************************************************************************************//**
     * @fn	@MutationMapping public SkillLevel createSkillLevel(@Argument int user_id, @Argument int skill_level, @Argument int sport_id)
     *
     * @brief	Creates skill level
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @exception	NotFoundException	Thrown when the requested element is not present.
     *
     * @param 	user_id	   	Identifier for the user.
     * @param 	skill_level	The skill level.
     * @param 	sport_id   	Identifier for the sport.
     *
     * @returns	The new skill level.
     **************************************************************************************************/

    @MutationMapping
    public SkillLevel createSkillLevel(@Argument int user_id, @Argument int skill_level, @Argument int sport_id) {
        Users user = userRepository.findById(user_id);
        if (user == null)
            throw new NotFoundException("Could not find user with id: " + user_id);

        Sport sport = sportRepository.findById(sport_id);
        if (sport == null)
            throw new NotFoundException("Could not find Sport with id: " + sport);

        SkillLevel skillLevel = new SkillLevel(skill_level, user, sport);
        user.getSkill_levels().add(skillLevel);
        skillLevelRepository.save(skillLevel);
        return skillLevel;
    }

    /**********************************************************************************************//**
     * @fn	@MutationMapping public SkillLevel updateSkillLevel(@Argument int skill_id, @Argument int skill_level , @Argument int sport_id)
     *
     * @brief	Updates the skill level
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @exception	NotFoundException	Thrown when the requested element is not present.
     *
     * @param 	skill_id   	Identifier for the skill.
     * @param 	skill_level	The skill level.
     * @param 	sport_id   	Identifier for the sport.
     *
     * @returns	A SkillLevel.
     **************************************************************************************************/

    @MutationMapping
    public SkillLevel updateSkillLevel(@Argument int skill_id, @Argument int skill_level , @Argument int sport_id) {
        SkillLevel skillLevel = skillLevelRepository.findById(skill_id);
        if (skillLevel == null)
            throw new NotFoundException("Could not find skillLevel with id: " + skill_id);
        Sport sport = sportRepository.findById(sport_id);
        if (sport == null)
            throw new NotFoundException("Could not find Sport with id: " + sport);

        skillLevel.setSport(sport);
        if(skill_level != 0) {
            skillLevel.setSkill_level(skill_level);
        }
        skillLevelRepository.save(skillLevel);
        return skillLevel;
    }

    /**********************************************************************************************//**
     * @fn	@MutationMapping public String deleteSkillLevelById(@Argument int id)
     *
     * @brief	Deletes the skill level by identifier described by ID
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @exception	NotFoundException	Thrown when the requested element is not present.
     *
     * @param 	id	The identifier.
     *
     * @returns	A String.
     **************************************************************************************************/

    @MutationMapping
    public String deleteSkillLevelById(@Argument int id) {
        SkillLevel skillLevel = skillLevelRepository.findById(id);
        if (skillLevel == null) {
            throw new NotFoundException("SkillLevel with id " + id + " not found.");
        }
        deleteSkillLevel(skillLevel);
        skillLevelRepository.deleteById(id);
        return "SkillLevel with id " + id + " has been deleted";
    }

    /**********************************************************************************************//**
     * @fn	@MutationMapping @Transactional public String deleteAllSkillLevels()
     *
     * @brief	Deletes all skill levels
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @returns	A String.
     **************************************************************************************************/

    @MutationMapping
    @Transactional
    public String deleteAllSkillLevels() {
        for(SkillLevel sk : skillLevelRepository.findAll()) {
            deleteSkillLevel(sk);
        }
        skillLevelRepository.deleteAll();
        return "All Skill Levels have been deleted";
    }

    /**********************************************************************************************//**
     * @fn	private void deleteSkillLevel(SkillLevel sk)
     *
     * @brief	Helper method used in deleting an individual skillLevel
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	sk	The sk.
     **************************************************************************************************/

    private void deleteSkillLevel(SkillLevel sk) {
        Sport sport = sk.getSport();
        if (sport != null) {
            sport.setSkillLevel(null);
            sportRepository.save(sport);
        }

        Users user = sk.getUser();
        if (user != null) {
            user.getSkill_levels().remove(sk);

            userRepository.save(user);
        }
    }
}
