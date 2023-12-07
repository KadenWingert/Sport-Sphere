

/** @brief	The coms 309. exceptions */
package coms309.Exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import coms309.Users.UserController;

/**********************************************************************************************/
/**
 * @class DuplicateException
 *
 * @brief Exception for signalling duplicate errors.
 *
 * @author Arie
 * @date 10/20/2023
 **************************************************************************************************/

public class DuplicateException extends RuntimeException {

        private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**********************************************************************************************/
    /**
     * @fn public DuplicateException(String message)
     *
     * @brief Constructor
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param message The message.
     **************************************************************************************************/

    public DuplicateException(String message) {
        super(message);
    }
}
