package fontys.sem3.individualProject.business.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidProductException extends ResponseStatusException {
    public InvalidProductException() {
        super(HttpStatus.BAD_REQUEST, "PRODUCT_INVALID");
    }

    public InvalidProductException(String message) {
        super(HttpStatus.BAD_REQUEST,message);
    }
}
