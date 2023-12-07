
package RetroFitAPI;

/** @brief	List of java.util.s */
import java.util.List;

/** @brief	Collection of models. features */
import models.FeatureCollection;
/** @brief	The retrofit 2. call */
import retrofit2.Call;
/** @brief	The retrofit 2.http. get */
import retrofit2.http.GET;
/** @brief	The retrofit 2.http. query */
import retrofit2.http.Query;

/**********************************************************************************************//**
 * @interface	RetrofitAPICall
 *
 * @brief	Interface for retrofit a pi call.
 *
 * @author	Arie
 * @date	10/20/2023
 **************************************************************************************************/

public interface RetrofitAPICall {
    // as we are making a get request specifying annotation as get and adding a url end point to it.
    @GET("search?addressdetails=1&limit=5&format=geojson")

    /**********************************************************************************************//**
     * @fn	Call<FeatureCollection> getFeatureCollection(@Query("q") String searchForAddress);
     *
     * @brief	on below line specifying the method name which we have to call.
     *
     * @author	Arie
     * @date	10/20/2023
     *
     * @param 	parameter1	The first parameter.
     *
     * @returns	The feature collection.
     **************************************************************************************************/

    Call<FeatureCollection> getFeatureCollection(@Query("q") String searchForAddress);
}
