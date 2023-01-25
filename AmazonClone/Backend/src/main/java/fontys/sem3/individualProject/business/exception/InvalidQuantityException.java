package fontys.sem3.individualProject.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidQuantityException extends ResponseStatusException {

    public InvalidQuantityException() {
            super(HttpStatus.BAD_REQUEST, "INVALID_QUANTITY");
        }

    public InvalidQuantityException(String message) {
            super(HttpStatus.BAD_REQUEST, message);
        }
}
