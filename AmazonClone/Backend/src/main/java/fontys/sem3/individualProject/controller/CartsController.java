package fontys.sem3.individualProject.controller;

import fontys.sem3.individualProject.business.CartsService;
import fontys.sem3.individualProject.business.exception.UnauthorizedDataAccessException;
import fontys.sem3.individualProject.configuration.security.isauthenticated.IsAuthenticated;
import fontys.sem3.individualProject.domain.AccessToken;
import fontys.sem3.individualProject.domain.AddProductRequest;
import fontys.sem3.individualProject.domain.Cart;
import fontys.sem3.individualProject.domain.DeleteProductRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/carts")
@AllArgsConstructor
//ToDO: try to make it global as the rest of the controllers
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class CartsController {

    private final CartsService cartByUseCases;
    private AccessToken requestAccessToken;

    @IsAuthenticated
    @RolesAllowed({"ROLE_CUSTOMER"})
    @GetMapping("/creator")
    public ResponseEntity<Cart> getCartByCreatorId() {
        final Optional<Cart> cartOptional = cartByUseCases.getCartByCreatorId(requestAccessToken.getUserId());
        if (cartOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(cartOptional.get());
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_CUSTOMER"})
    @PutMapping("/addProduct")
    public ResponseEntity<Void> addProduct(@RequestBody @Valid AddProductRequest request) {
        if (requestAccessToken.getUserId() != request.getCreatorId()) {
            throw new UnauthorizedDataAccessException("USER_ID_NOT_FROM_LOGGED_IN_USER");
        }

        cartByUseCases.addProduct(request);
        return ResponseEntity.noContent().build();
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_CUSTOMER"})
    @DeleteMapping("/deleteProduct")
    public ResponseEntity<Void> deleteProduct(@RequestBody @Valid DeleteProductRequest request) {
        if (requestAccessToken.getUserId() != request.getCreatorId()) {
            throw new UnauthorizedDataAccessException("USER_ID_NOT_FROM_LOGGED_IN_USER");
        }

        cartByUseCases.deleteProduct(request);
        return ResponseEntity.noContent().build();
    }
}
