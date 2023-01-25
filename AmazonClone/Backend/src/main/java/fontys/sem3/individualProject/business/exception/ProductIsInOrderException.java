package fontys.sem3.individualProject.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ProductIsInOrderException extends ResponseStatusException {
    public ProductIsInOrderException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
