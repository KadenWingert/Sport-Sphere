

/** @brief	The coms 309. exceptions */
package coms309.Exceptions;

/** @brief The org.springframework.http. HTTP status */
import org.springframework.http.HttpStatus;
/** @brief The org.springframework.web.bind.annotation. response status */
import org.springframework.web.bind.annotation.ResponseStatus;

/**********************************************************************************************/
/**
 * @fn @ResponseStatus(value = HttpStatus.NOT_FOUND) public class NotFoundException extends
 *     RuntimeException
 *
 * @brief Constructor
 *
 * @author Arie
 * @date 10/20/2023
 *
 * @param parameter1 (Optional) The first parameter.
 **************************************************************************************************/

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    /**********************************************************************************************/
    /**
     * @fn public NotFoundException(String message)
     *
     * @brief Constructor
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param message The message.
     **************************************************************************************************/

    public NotFoundException(String message) {
        super(message);
    }
}
