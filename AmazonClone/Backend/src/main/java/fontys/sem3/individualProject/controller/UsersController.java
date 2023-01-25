package fontys.sem3.individualProject.controller;

import fontys.sem3.individualProject.business.UsersService;
import fontys.sem3.individualProject.business.exception.UnauthorizedDataAccessException;
import fontys.sem3.individualProject.configuration.security.isauthenticated.IsAuthenticated;
import fontys.sem3.individualProject.domain.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UsersController {

    private final UsersService usersService;
    private AccessToken requestAccessToken;


    @IsAuthenticated
    @GetMapping
    public ResponseEntity<User> getUser() {
        final Optional<User> userOptional = usersService.getUserById(requestAccessToken.getUserId());
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(userOptional.get());
    }

    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody @Valid CreateUserRequest request) {
        CreateUserResponse response = usersService.createCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        LoginResponse loginResponse = usersService.login(request);
        return ResponseEntity.ok(loginResponse);
    }
}
