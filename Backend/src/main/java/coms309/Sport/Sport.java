

/** @brief	The coms 309. sport */
package coms309.Sport;

/** @brief	The coms 309. game post. game post */
import coms309.GamePost.GamePost;
/** @brief	The coms 309. skill level. skill level */
import coms309.SkillLevel.SkillLevel;
/** @brief	The jakarta.persistence.* */
import jakarta.persistence.*;
/** @brief	The lombok. getter */
import lombok.Getter;
/** @brief	The lombok. no arguments constructor */
import lombok.NoArgsConstructor;
/** @brief	The lombok. setter */
import lombok.Setter;

/** @brief	Set the java.util. hash belongs to */
import java.util.HashSet;
/** @brief	Set the java.util. belongs to */
import java.util.Set;

/**********************************************************************************************//**
 * @class	Sport
 *
 * @brief	A sport.
 *
 * @author	Arie
 * @date	10/20/2023
 **************************************************************************************************/

@Entity
@Getter @Setter @NoArgsConstructor
public class Sport {

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

    /** @brief	/** @brief	Name of the sport */
    private String sport_name;

    /**********************************************************************************************//**
     * @fn	@OneToMany(mappedBy = "sport",fetch = FetchType.EAGER) private Set<GamePost> gamePosts = new HashSet<>();
     *
     * @brief	Constructor
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	parameter1	(Optional) The first parameter.
     * @param 	parameter2	(Optional) The second parameter.
     **************************************************************************************************/

    @OneToMany(mappedBy = "sport",fetch = FetchType.EAGER)
    private Set<GamePost> gamePosts = new HashSet<>();

    /**********************************************************************************************//**
     * @fn	@OneToOne @JoinColumn(name = "skill_level_id") private SkillLevel skillLevel;
     *
     * @brief	Constructor
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	parameter1	(Optional) The first parameter.
     **************************************************************************************************/

    @OneToOne
    @JoinColumn(name = "skill_level_id")
    private SkillLevel skillLevel;

    /**********************************************************************************************//**
     * @fn	public Sport(String sport_name)
     *
     * @brief	Constructor
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	sport_name	Name of the sport.
     **************************************************************************************************/

    public Sport(String sport_name) {
        this.sport_name = sport_name;
    }

    /**********************************************************************************************//**
     * @fn	public Sport(String sport_name, SkillLevel skillLevel)
     *
     * @brief	Constructor
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	sport_name	Name of the sport.
     * @param 	skillLevel	The skill level.
     **************************************************************************************************/

    public Sport(String sport_name, SkillLevel skillLevel) {
        this.sport_name = sport_name;
        this.skillLevel = skillLevel;
    }
}

