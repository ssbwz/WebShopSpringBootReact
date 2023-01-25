package fontys.sem3.individualProject.business;

import fontys.sem3.individualProject.domain.*;

import java.util.Optional;

public interface UsersService {
    Optional<User> getUserById(long id);
    CreateUserResponse createCustomer(CreateUserRequest request) ;

    LoginResponse login(LoginRequest loginRequest);
}
