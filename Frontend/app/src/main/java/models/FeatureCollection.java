
package models;

/** @brief	List of java.util.s */
import java.util.List;

/**********************************************************************************************//**
 * @class	FeatureCollection
 *
 * @brief	Collection of features.
 *
 * @author	Arie
 * @date	10/20/2023
 **************************************************************************************************/

public class FeatureCollection {
    /** @brief	The type */
    private String type;
    /** @brief	The licence */
    private String licence;
    /** @brief	The features */
    private List<Feature> features;

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
     * @fn	public String getLicence()
     *
     * @brief	Gets the licence
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @returns	The licence.
     **************************************************************************************************/

    public String getLicence() {
        return licence;
    }

    /**********************************************************************************************//**
     * @fn	public void setLicence(String licence)
     *
     * @brief	Sets a licence
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	licence	The licence.
     **************************************************************************************************/

    public void setLicence(String licence) {
        this.licence = licence;
    }

    /**********************************************************************************************//**
     * @fn	public List<Feature> getFeatures()
     *
     * @brief	Gets the features
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @returns	The features.
     **************************************************************************************************/

    public List<Feature> getFeatures() {
        return features;
    }

    /**********************************************************************************************//**
     * @fn	public void setFeatures(List<Feature> features)
     *
     * @brief	Sets the features
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	features	The features.
     **************************************************************************************************/

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

}