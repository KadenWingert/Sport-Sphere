
package models;

/**********************************************************************************************//**
 * @class	Address
 *
 * @brief	An address.
 *
 * @author	Arie
 * @date	10/20/2023
 **************************************************************************************************/

public class Address {
    /** @brief	The house number */
    private String house_number;
    /** @brief	The highway */
    private String highway;
    /** @brief	The road */
    private String road;
    /** @brief	The neighbourhood */
    private String neighbourhood;
    /** @brief	The city */
    private String city;
    /** @brief	The county */
    private String county;
    /** @brief	The state */
    private String state;
    /** @brief	The fourth ISO 3166 2 level */
    private String ISO3166_2_lvl4;
    /** @brief	The postcode */
    private String postcode;
    /** @brief	The country */
    private String country;
    /** @brief	The country code */
    private String country_code;

    /**********************************************************************************************//**
     * @fn	public String getHouse_number()
     *
     * @brief	Getters and setters
     * 			...
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @returns	The house number.
     **************************************************************************************************/

    public String getHouse_number() {
        return house_number;
    }

    /**********************************************************************************************//**
     * @fn	public void setHouse_number(String house_number)
     *
     * @brief	Sets house number
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	house_number	The house number.
     **************************************************************************************************/

    public void setHouse_number(String house_number) {
        this.house_number = house_number;
    }

    /**********************************************************************************************//**
     * @fn	public String getHighway()
     *
     * @brief	Gets the highway
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @returns	The highway.
     **************************************************************************************************/

    public String getHighway() {
        return highway;
    }

    /**********************************************************************************************//**
     * @fn	public void setHighway(String highway)
     *
     * @brief	Sets a highway
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	highway	The highway.
     **************************************************************************************************/

    public void setHighway(String highway) {
        this.highway = highway;
    }

    /**********************************************************************************************//**
     * @fn	public String getRoad()
     *
     * @brief	Gets the road
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @returns	The road.
     **************************************************************************************************/

    public String getRoad() {
        return road;
    }

    /**********************************************************************************************//**
     * @fn	public void setRoad(String road)
     *
     * @brief	Sets a road
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	road	The road.
     **************************************************************************************************/

    public void setRoad(String road) {
        this.road = road;
    }

    /**********************************************************************************************//**
     * @fn	public String getNeighbourhood()
     *
     * @brief	Gets the neighbourhood
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @returns	The neighbourhood.
     **************************************************************************************************/

    public String getNeighbourhood() {
        return neighbourhood;
    }

    /**********************************************************************************************//**
     * @fn	public void setNeighbourhood(String neighbourhood)
     *
     * @brief	Sets a neighbourhood
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	neighbourhood	The neighbourhood.
     **************************************************************************************************/

    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    /**********************************************************************************************//**
     * @fn	public String getCity()
     *
     * @brief	Gets the city
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @returns	The city.
     **************************************************************************************************/

    public String getCity() {
        return city;
    }

    /**********************************************************************************************//**
     * @fn	public void setCity(String city)
     *
     * @brief	Sets a city
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	city	The city.
     **************************************************************************************************/

    public void setCity(String city) {
        this.city = city;
    }

    /**********************************************************************************************//**
     * @fn	public String getState()
     *
     * @brief	Gets the state
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @returns	The state.
     **************************************************************************************************/

    public String getState() {
        return state;
    }

    /**********************************************************************************************//**
     * @fn	public void setState(String state)
     *
     * @brief	Sets a state
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	state	The state.
     **************************************************************************************************/

    public void setState(String state) {
        this.state = state;
    }

    /**********************************************************************************************//**
     * @fn	public String getCounty()
     *
     * @brief	Gets the county
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @returns	The county.
     **************************************************************************************************/

    public String getCounty() {
        return county;
    }

    /**********************************************************************************************//**
     * @fn	public void setCounty(String county)
     *
     * @brief	Sets a county
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	county	The county.
     **************************************************************************************************/

    public void setCounty(String county) {
        this.county = county;
    }

    /**********************************************************************************************//**
     * @fn	public String getPostcode()
     *
     * @brief	Gets the postcode
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @returns	The postcode.
     **************************************************************************************************/

    public String getPostcode() {
        return postcode;
    }

    /**********************************************************************************************//**
     * @fn	public void setPostcode(String postcode)
     *
     * @brief	Sets a postcode
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	postcode	The postcode.
     **************************************************************************************************/

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    /**********************************************************************************************//**
     * @fn	public String getCountry()
     *
     * @brief	Gets the country
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @returns	The country.
     **************************************************************************************************/

    public String getCountry() {
        return country;
    }

    /**********************************************************************************************//**
     * @fn	public void setCountry(String country)
     *
     * @brief	Sets a country
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	country	The country.
     **************************************************************************************************/

    public void setCountry(String country) {
        this.country = country;
    }

    /**********************************************************************************************//**
     * @fn	public String getCountry_code()
     *
     * @brief	Gets country code
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @returns	The country code.
     **************************************************************************************************/

    public String getCountry_code() {
        return country_code;
    }

    /**********************************************************************************************//**
     * @fn	public void setCountry_code(String country_code)
     *
     * @brief	Sets country code
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	country_code	The country code.
     **************************************************************************************************/

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }
    // Add getters and setters for other properties
}
