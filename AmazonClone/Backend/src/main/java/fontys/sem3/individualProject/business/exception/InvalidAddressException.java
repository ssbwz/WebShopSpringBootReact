package fontys.sem3.individualProject.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidAddressException extends ResponseStatusException {
    public InvalidAddressException() {
        super(HttpStatus.BAD_REQUEST, "ADDRESS_INVALID");
    }

    public InvalidAddressException(String message) {
        super(HttpStatus.BAD_REQUEST,message);
    }
}
