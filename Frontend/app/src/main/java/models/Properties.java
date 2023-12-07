
package models;

/**********************************************************************************************//**
 * @class	Properties
 *
 * @brief	A properties.
 *
 * @author	Arie
 * @date	10/20/2023
 **************************************************************************************************/

public class Properties {
    /** @brief	Identifier for the place */
    private String place_id;
    /** @brief	Type of the osm */
    private String osm_type;
    /** @brief	Identifier for the osm */
    private String osm_id;
    /** @brief	The place rank */
    private String place_rank;
    /** @brief	The category */
    private String category;
    /** @brief	The type */
    private String type;
    /** @brief	The importance */
    private String importance;
    /** @brief	The addresstype */
    private String addresstype;
    /** @brief	The name */
    private String name;
    /** @brief	Name of the display */
    private String display_name;
    /** @brief	The address */
    private Address address;

    /**********************************************************************************************//**
     * @fn	public Address getAddress()
     *
     * @brief	Getters and setters
     * 			...
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @returns	The address.
     **************************************************************************************************/

    public Address getAddress() {
        return address;
    }

    /**********************************************************************************************//**
     * @fn	public void setAddress(Address address)
     *
     * @brief	Sets the address
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	address	The address.
     **************************************************************************************************/

    public void setAddress(Address address) {
        this.address = address;
    }

    /**********************************************************************************************//**
     * @fn	public String getPlace_id()
     *
     * @brief	Gets place identifier
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @returns	The place identifier.
     **************************************************************************************************/

    public String getPlace_id() {
        return place_id;
    }

    /**********************************************************************************************//**
     * @fn	public void setPlace_id(String place_id)
     *
     * @brief	Sets place identifier
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	place_id	Identifier for the place.
     **************************************************************************************************/

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    /**********************************************************************************************//**
     * @fn	public String getOsm_type()
     *
     * @brief	Gets osm type
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @returns	The osm type.
     **************************************************************************************************/

    public String getOsm_type() {
        return osm_type;
    }

    /**********************************************************************************************//**
     * @fn	public void setOsm_type(String osm_type)
     *
     * @brief	Sets osm type
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	osm_type	Type of the osm.
     **************************************************************************************************/

    public void setOsm_type(String osm_type) {
        this.osm_type = osm_type;
    }

    /**********************************************************************************************//**
     * @fn	public String getOsm_id()
     *
     * @brief	Gets osm identifier
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @returns	The osm identifier.
     **************************************************************************************************/

    public String getOsm_id() {
        return osm_id;
    }

    /**********************************************************************************************//**
     * @fn	public void setOsm_id(String osm_id)
     *
     * @brief	Sets osm identifier
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	osm_id	Identifier for the osm.
     **************************************************************************************************/

    public void setOsm_id(String osm_id) {
        this.osm_id = osm_id;
    }

    /**********************************************************************************************//**
     * @fn	public String getPlace_rank()
     *
     * @brief	Gets place rank
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @returns	The place rank.
     **************************************************************************************************/

    public String getPlace_rank() {
        return place_rank;
    }

    /**********************************************************************************************//**
     * @fn	public void setPlace_rank(String place_rank)
     *
     * @brief	Sets place rank
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	place_rank	The place rank.
     **************************************************************************************************/

    public void setPlace_rank(String place_rank) {
        this.place_rank = place_rank;
    }

    /**********************************************************************************************//**
     * @fn	public String getCategory()
     *
     * @brief	Gets the category
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @returns	The category.
     **************************************************************************************************/

    public String getCategory() {
        return category;
    }

    /**********************************************************************************************//**
     * @fn	public void setCategory(String category)
     *
     * @brief	Sets a category
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	category	The category.
     **************************************************************************************************/

    public void setCategory(String category) {
        this.category = category;
    }

    /**********************************************************************************************//**
     * @fn	public String getType()
     *
     * @brief	Gets the type
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
     * @fn	public String getImportance()
     *
     * @brief	Gets the importance
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @returns	The importance.
     **************************************************************************************************/

    public String getImportance() {
        return importance;
    }

    /**********************************************************************************************//**
     * @fn	public void setImportance(String importance)
     *
     * @brief	Sets an importance
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	importance	The importance.
     **************************************************************************************************/

    public void setImportance(String importance) {
        this.importance = importance;
    }

    /**********************************************************************************************//**
     * @fn	public String getAddresstype()
     *
     * @brief	Gets the addresstype
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @returns	The addresstype.
     **************************************************************************************************/

    public String getAddresstype() {
        return addresstype;
    }

    /**********************************************************************************************//**
     * @fn	public void setAddresstype(String addresstype)
     *
     * @brief	Sets an addresstype
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	addresstype	The addresstype.
     **************************************************************************************************/

    public void setAddresstype(String addresstype) {
        this.addresstype = addresstype;
    }

    /**********************************************************************************************//**
     * @fn	public String getName()
     *
     * @brief	Gets the name
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @returns	The name.
     **************************************************************************************************/

    public String getName() {
        return name;
    }

    /**********************************************************************************************//**
     * @fn	public void setName(String name)
     *
     * @brief	Sets a name
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	name	The name.
     **************************************************************************************************/

    public void setName(String name) {
        this.name = name;
    }

    /**********************************************************************************************//**
     * @fn	public String getDisplay_name()
     *
     * @brief	Gets display name
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @returns	The display name.
     **************************************************************************************************/

    public String getDisplay_name() {
        return display_name;
    }

    /**********************************************************************************************//**
     * @fn	public void setDisplay_name(String display_name)
     *
     * @brief	Sets display name
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	display_name	Name of the display.
     **************************************************************************************************/

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }
}


