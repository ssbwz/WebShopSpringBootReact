package fontys.sem3.individualProject.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidUserException extends ResponseStatusException {
    public InvalidUserException() {
        super(HttpStatus.BAD_REQUEST, "USER_INVALID");
    }

    public InvalidUserException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
