
package models;

/** @brief	List of java.util.s */
import java.util.List;

/**********************************************************************************************//**
 * @class	Feature
 *
 * @brief	A feature.
 *
 * @author	Arie
 * @date	10/20/2023
 **************************************************************************************************/

public class Feature {
    /** @brief	The type */
    private String type;
    /** @brief	The properties */
    private Properties properties;
    /** @brief	The bounding box */
    private List<Double> bbox;
    /** @brief	The geometry */
    private Geometry geometry;

    /**********************************************************************************************//**
     * @fn	public String getType()
     *
     * @brief	Getters and setters
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
     * @fn	public Properties getProperties()
     *
     * @brief	Gets the properties
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @returns	The properties.
     **************************************************************************************************/

    public Properties getProperties() {
        return properties;
    }

    /**********************************************************************************************//**
     * @fn	public void setProperties(Properties properties)
     *
     * @brief	Sets the properties
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	properties	The properties.
     **************************************************************************************************/

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    /**********************************************************************************************//**
     * @fn	public List<Double> getBbox()
     *
     * @brief	Gets the bounding box
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @returns	The bounding box.
     **************************************************************************************************/

    public List<Double> getBbox() {
        return bbox;
    }

    /**********************************************************************************************//**
     * @fn	public void setBbox(List<Double> bbox)
     *
     * @brief	Sets a bounding box
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	bbox	The bounding box.
     **************************************************************************************************/

    public void setBbox(List<Double> bbox) {
        this.bbox = bbox;
    }

    /**********************************************************************************************//**
     * @fn	public Geometry getGeometry()
     *
     * @brief	Gets the geometry
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @returns	The geometry.
     **************************************************************************************************/

    public Geometry getGeometry() {
        return geometry;
    }

    /**********************************************************************************************//**
     * @fn	public void setGeometry(Geometry geometry)
     *
     * @brief	Sets a geometry
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	geometry	The geometry.
     **************************************************************************************************/

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }
}

