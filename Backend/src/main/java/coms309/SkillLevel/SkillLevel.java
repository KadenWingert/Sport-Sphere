

/** @brief	The coms 309. skill level */
package coms309.SkillLevel;

/** @brief	The coms 309. sport. sport */
import coms309.Sport.Sport;
/** @brief	The coms 309. users. users */
import coms309.Users.Users;
/** @brief	The jakarta.persistence.* */
import jakarta.persistence.*;
/** @brief	The lombok. getter */
import lombok.Getter;
/** @brief	The lombok. no arguments constructor */
import lombok.NoArgsConstructor;
/** @brief	The lombok. setter */
import lombok.Setter;
/** @brief	The jakarta.persistence. entity */
import jakarta.persistence.Entity;
/** @brief	The jakarta.persistence. generated value */
import jakarta.persistence.GeneratedValue;
/** @brief	Type of the jakarta.persistence. generation */
import jakarta.persistence.GenerationType;
/** @brief	Identifier for the jakarta.persistence. */
import jakarta.persistence.Id;

/** @brief	Set the java.util. hash belongs to */
import java.util.HashSet;
/** @brief	Set the java.util. belongs to */
import java.util.Set;

/**********************************************************************************************//**
 * @class	SkillLevel
 *
 * @brief	A skill level.
 *
 * @author	Arie
 * @date	10/20/2023
 **************************************************************************************************/

@Entity
@Getter @Setter @NoArgsConstructor
public class SkillLevel {

    /**********************************************************************************************//**
     * @fn	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id") private int id;
     *
     * @brief	Constructor
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	parameter1	(Optional) The first parameter.
     **************************************************************************************************/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    /** @brief	/** @brief	The skill level */
    private int skill_level;

    /**********************************************************************************************//**
     * @fn	@ManyToOne @JoinColumn(name = "user_id") private Users user;
     *
     * @brief	Constructor
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	parameter1	(Optional) The first parameter.
     **************************************************************************************************/

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    /**********************************************************************************************//**
     * @property	@OneToOne private Sport sport
     *
     * @brief	Gets the sport
     *
     * @returns	The sport.
     **************************************************************************************************/

    @OneToOne
    private Sport sport;

    /**********************************************************************************************//**
     * @fn	public SkillLevel(int skill_level, Users user)
     *
     * @brief	Constructor
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	skill_level	The skill level.
     * @param 	user	   	The user.
     **************************************************************************************************/

    public SkillLevel(int skill_level, Users user) {
        this.skill_level = skill_level;
        this.user = user;
    }

    /**********************************************************************************************//**
     * @fn	public SkillLevel(int skill_level, Users user, Sport sport)
     *
     * @brief	Constructor
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	skill_level	The skill level.
     * @param 	user	   	The user.
     * @param 	sport	   	The sport.
     **************************************************************************************************/

    public SkillLevel(int skill_level, Users user, Sport sport) {
        this.skill_level = skill_level;
        this.user = user;
        this.sport = sport;
    }

    /**********************************************************************************************//**
     * @fn	public int getId()
     *
     * @brief	Gets the identifier
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @returns	int.
     **************************************************************************************************/

    public int getId() {
        return id;
    }

    /**********************************************************************************************//**
     * @fn	public void setId(int skill_level_id)
     *
     * @brief	Sets an identifier
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	skill_level_id	Identifier for the skill level.
     **************************************************************************************************/

    public void setId(int skill_level_id) {
        this.id = skill_level_id;
    }

    /**********************************************************************************************//**
     * @fn	public int getSkill_level()
     *
     * @brief	Gets skill level
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @returns	The skill level.
     **************************************************************************************************/

    public int getSkill_level() {
        return skill_level;
    }

    /**********************************************************************************************//**
     * @fn	public void setSkill_level(int skill_level)
     *
     * @brief	Sets skill level
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	skill_level	The skill level.
     **************************************************************************************************/

    public void setSkill_level(int skill_level) {
        this.skill_level = skill_level;
    }

    /**********************************************************************************************//**
     * @fn	public Users getUser()
     *
     * @brief	Gets the user
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @returns	The user.
     **************************************************************************************************/

    public Users getUser() {
        return user;
    }

    /**********************************************************************************************//**
     * @fn	public void setUser(Users user)
     *
     * @brief	Sets a user
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	user	The user.
     **************************************************************************************************/

    public void setUser(Users user) {
        this.user = user;
    }
}
