
package models;

/** @brief	List of java.util.s */
import java.util.List;

/**********************************************************************************************//**
 * @class	Geometry
 *
 * @brief	A geometry.
 *
 * @author	Arie
 * @date	10/20/2023
 **************************************************************************************************/

public class Geometry {
    /** @brief	The type */
    private String type;
    /** @brief	The coordinates */
    private List<Double> coordinates;

    /**********************************************************************************************//**
     * @fn	public String getType()
     *
     * @brief	Getters and setters
     * 			...
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @returns	The type.
     **************************************************************************************************/

    public String getType() {
        return type;
    }

    /**********************************************************************************************//**
     * @fn	public void setType(String type)
     *
     * @brief	Sets a type
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	type	The type.
     **************************************************************************************************/

    public void setType(String type) {
        this.type = type;
    }

    /**********************************************************************************************//**
     * @fn	public List<Double> getCoordinates()
     *
     * @brief	Gets the coordinates
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @returns	The coordinates.
     **************************************************************************************************/

    public List<Double> getCoordinates() {
        return coordinates;
    }

    /**********************************************************************************************//**
     * @fn	public void setCoordinates(List<Double> coordinates)
     *
     * @brief	Sets the coordinates
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	coordinates	The coordinates.
     **************************************************************************************************/

    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }

}

