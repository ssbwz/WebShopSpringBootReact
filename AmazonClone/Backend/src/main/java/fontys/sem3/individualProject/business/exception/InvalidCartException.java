package fontys.sem3.individualProject.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidCartException extends ResponseStatusException {
    public InvalidCartException() {
        super(HttpStatus.BAD_REQUEST, "CART_INVALID");
    }

    public InvalidCartException(String message) {
        super(HttpStatus.BAD_REQUEST,message);
    }
}
