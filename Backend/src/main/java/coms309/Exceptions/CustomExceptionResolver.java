package coms309.Exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;
import coms309.Users.UserController;
import graphql.GraphQLError;
import graphql.GraphQLException;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;

@Component
public class CustomExceptionResolver extends DataFetcherExceptionResolverAdapter {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof NotFoundException) {
            logger.error(ex.getMessage(), ex);
            return GraphqlErrorBuilder.newError().errorType(ErrorType.NOT_FOUND)
                    .message("Errors are bad. " + ex.getMessage())
                    .path(env.getExecutionStepInfo().getPath())
                    .location(env.getField().getSourceLocation()).build();
        } else if (ex instanceof DuplicateException) {
            logger.error(ex.getMessage(), ex);

            return GraphqlErrorBuilder.newError().errorType(ErrorType.FORBIDDEN)
                    .message("Duplicates not permitted. " + ex.getMessage())
                    .path(env.getExecutionStepInfo().getPath())
                    .location(env.getField().getSourceLocation()).build();
        } else if (ex instanceof GraphQLException) {
            logger.error(ex.getMessage(), ex);

            return GraphqlErrorBuilder.newError().errorType(ErrorType.INTERNAL_ERROR)
                    .message("GraphQL error. " + ex.getMessage())
                    .path(env.getExecutionStepInfo().getPath())
                    .location(env.getField().getSourceLocation()).build();
        } else {
            logger.debug("Error happened, caught at custom error handler.");
            return null;
        }
    }
}
