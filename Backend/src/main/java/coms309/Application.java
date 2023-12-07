

/** @brief The coms 309 */
package coms309;


/** @brief The org.springframework.boot. spring application */
import org.springframework.boot.SpringApplication;
/** @brief The org.springframework.boot.autoconfigure. spring boot application */
import org.springframework.boot.autoconfigure.SpringBootApplication;
/** @brief The org.slf 4j. logger */
import org.slf4j.Logger;
/** @brief The org.slf 4j. logger factory */
import org.slf4j.LoggerFactory;

/**********************************************************************************************/
/**
 * @class Application
 *
 * @brief An application.
 *
 * @author Arie
 * @date 10/20/2023
 **************************************************************************************************/

@SpringBootApplication
public class Application {

    /** @brief /** @brief The logger */
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    /**********************************************************************************************/
    /**
     * @fn public static void main(String[] args) throws Exception
     *
     * @brief Main entry-point for this application
     *
     * @author Arie
     * @date 10/20/2023
     *
     * @param args .
     **************************************************************************************************/

    public static void main(String[] args) throws Exception {
        try {
            SpringApplication.run(Application.class, args);
        } catch (Exception e) {
            // TODO: handle exception
            logger.error(e.getMessage(), e);
        }
    }
}


