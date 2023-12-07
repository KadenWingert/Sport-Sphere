
/** @brief The coms 309. email */
package coms309.Email;

/** @brief The java.util. optional */
import java.util.Optional;
/** @brief The coms 309. exceptions. not found exception */
import coms309.Exceptions.NotFoundException;
/** @brief The coms 309. users. users */
import coms309.Users.Users;
/** @brief The org.slf 4j. logger */
import org.slf4j.Logger;
/** @brief The org.slf 4j. logger factory */
import org.slf4j.LoggerFactory;

/** @brief The com.mailgun.api.v 3. mailgun messages API */
import com.mailgun.api.v3.MailgunMessagesApi;
/** @brief The com.mailgun.client. mailgun client */
import com.mailgun.client.MailgunClient;
/** @brief Message describing the com.mailgun.model.message. */
import com.mailgun.model.message.Message;
/** @brief The com.mailgun.model.message. message response */
import com.mailgun.model.message.MessageResponse;

// You can see a record of this email in your logs: https://app.mailgun.com/app/logs.

/**********************************************************************************************/
/**
 * @class EmailSender
 *
 * @brief You can send up to 300 emails/day from this sandbox server. Next, you should add your own
 *        domain so you can send 10000 emails/month for free.
 *
 * @author Arie
 * @date 10/20/2023
 **************************************************************************************************/

public final class EmailSender extends Exception {

        /** @brief /** @brief The logger */
        private static final Logger logger = LoggerFactory.getLogger(EmailSender.class);

        /**********************************************************************************************/
        /**
         * @fn public static void SendEmail(Users user, String subject, String msg)
         *
         * @brief Sends an email
         *
         * @author Arie
         * @date 10/20/2023
         *
         * @param user .
         * @param subject .
         * @param msg .
         **************************************************************************************************/

        public static void SendEmail(Users user, String subject, String msg) {
                try {
                        sendSimpleMessage(user, subject, msg);

                        // sendSimpleMessage2(user);
                        // Client client = Client.create();

                        // client.addFilter(new HTTPBasicAuthFilter("api", getApiKey()));

                        // WebResource webResource = client.resource(
                        // "https://api.mailgun.net/v3/sandbox15357e699103427fa117c2ea75d7c6ba.mailgun.org/messages");

                        // MultivaluedMapImpl formData = new MultivaluedMapImpl();
                        // formData.add("from", "SportSphere <noreply@sportsphere.com>");
                        // formData.add("to", user.getEmail());
                        // formData.add("subject", subject);
                        // formData.add("text", msg);

                        // webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class,
                        // formData);

                } catch (Exception e) {
                        // TODO: handle exception
                        System.out.println("error sending email. " + e.getMessage());
                        logger.error(e.getMessage(), e);
                }
        }

        /**********************************************************************************************/
        /**
         * @fn public static MessageResponse sendSimpleMessage(Users user, String subject, String
         *     msg)
         *
         * @brief Sends a simple message
         *
         * @author Arie
         * @date 10/20/2023
         *
         * @param user The user.
         * @param subject The subject.
         * @param msg The message.
         *
         * @returns A MessageResponse.
         **************************************************************************************************/

        public static MessageResponse sendSimpleMessage(Users user, String subject, String msg) {
                MailgunMessagesApi mailgunMessagesApi = MailgunClient.config(getApiKey())
                                .createApi(MailgunMessagesApi.class);

                Message message = Message.builder().from("SportSphere <noreply@sportsphere.com>")
                                .to(user.getEmail()).subject(subject).text(msg).build();

                return mailgunMessagesApi.sendMessage(
                                "sandbox15357e699103427fa117c2ea75d7c6ba.mailgun.org", message);
        }

        /**********************************************************************************************/
        /**
         * @fn private static String getApiKey()
         *
         * @brief Gets API key
         *
         * @author Arie
         * @date 10/20/2023
         *
         * @returns String.
         **************************************************************************************************/

        private static String getApiKey() {
                String apiKey = null;
                try {
                        apiKey = Optional.ofNullable(System.getenv("MAIL_API_KEY"))
                                        .orElseThrow(() -> new NotFoundException(
                                                        "MAIL_API_KEY was not found in the environment"));

                } catch (Exception e) {
                        // TODO: handle exception
                        logger.error(e.getMessage(), e);
                }

                return apiKey;
        }
}
