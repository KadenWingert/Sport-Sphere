package coms309.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class SubscriptionException extends RuntimeException {

    public SubscriptionException(String message) {
        super(message);
    }
}
