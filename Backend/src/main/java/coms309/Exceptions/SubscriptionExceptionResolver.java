package coms309.Exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.graphql.execution.SubscriptionExceptionResolverAdapter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import coms309.Users.UserController;
import graphql.GraphQLError;
import graphql.GraphQLException;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import graphql.validation.ValidationError;

@Component
public class SubscriptionExceptionResolver extends SubscriptionExceptionResolverAdapter {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex) {
        if (ex instanceof IllegalStateException) {
            logger.error(ex.getMessage(), ex);

            return GraphqlErrorBuilder.newError().errorType(ErrorType.BAD_REQUEST)
                    .message("Oops, that didn't work. " + ex.getMessage()).build();
        } else if (ex instanceof GraphQLException) {
            logger.error(ex.getMessage(), ex);

            return GraphqlErrorBuilder.newError().errorType(ErrorType.INTERNAL_ERROR)
                    .message("Oops, that didn't work. " + ex.getMessage()).build();
        } else if (ex instanceof SubscriptionException) {
            logger.error(ex.getMessage(), ex);
            return GraphqlErrorBuilder.newError().errorType(ErrorType.INTERNAL_ERROR)
                    .message("Oops, that didn't work. Subscription error. " + ex.getMessage())
                    .build();

        } else if (ex instanceof ResponseStatusException) {
            logger.error(ex.getMessage(), ex);

            return GraphqlErrorBuilder.newError().errorType(ErrorType.FORBIDDEN)
                    .message("Oops, that didn't work. " + ex.getMessage()).build();
        } else if (ex instanceof IllegalArgumentException) {
            logger.error(ex.getMessage(), ex);

            return GraphqlErrorBuilder.newError().errorType(ErrorType.BAD_REQUEST)
                    .message("Illegal Argument. " + ex.getMessage()).build();
        } else if (ex instanceof NullPointerException) {
            logger.error(ex.getMessage(), ex);

            return GraphqlErrorBuilder.newError().errorType(ErrorType.INTERNAL_ERROR)
                    .message("Null pointer. " + ex.getMessage()).build();
        } else if (ex instanceof IndexOutOfBoundsException) {
            logger.error(ex.getMessage(), ex);

            return GraphqlErrorBuilder.newError().errorType(ErrorType.INTERNAL_ERROR)
                    .message("Index out of bounds. " + ex.getMessage()).build();
        } else if (ex instanceof NotFoundException) {
            logger.error(ex.getMessage(), ex);

            return GraphqlErrorBuilder.newError().errorType(ErrorType.NOT_FOUND)
                    .message("Not found. " + ex.getMessage()).build();
        } else {
            logger.debug("Error happened, caught at subscription error handler.");

            return null;
        }
    }
}
