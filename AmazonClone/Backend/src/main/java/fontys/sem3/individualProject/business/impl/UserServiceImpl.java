package fontys.sem3.individualProject.business.impl;

import fontys.sem3.individualProject.business.AccessTokenEncoder;
import fontys.sem3.individualProject.business.exception.InvalidCredentialsException;
import fontys.sem3.individualProject.domain.*;
import fontys.sem3.individualProject.business.UsersService;
import fontys.sem3.individualProject.persistence.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UsersService {

    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenEncoder accessTokenEncoder;

    @Override
    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public CreateUserResponse createCustomer(CreateUserRequest request) {
        accountDetailsValidator(request.getEmail(),request.getPassword(),request.getFirstname(),request.getLastname());
        User customer = saveCustomer(request);
        return CreateUserResponse.builder().userId(customer.getId()).build();
    }

    private User saveCustomer(CreateUserRequest request) {
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .userType(UserType.Customer)
                .build();
        return userRepository.save(user);
    }

    private void accountDetailsValidator(String gmail, String password, String firstname, String lastname) {
        //ToDo: work in the regular expressions
        /*
        Pattern pattern = Pattern.compile("/^[1-9][0-9]{3} ?(?!sa|sd|ss)[a-z]{2}$", Pattern.CASE_INSENSITIVE);
        if(!pattern.matcher(address.getPostcode()).find()){
            throw  new InvalidAddressException("Postcode address is invalid");
        }
        if(!pattern.matches("^[1-9]\\d*(?:[ -]?(?:[a-zA-Z]+|[1-9]\\d*))?$", address.getHouseNumber())){
            throw  new InvalidAddressException("House number address is invalid");
        }
         */
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());
        if (user == null) {
            throw new InvalidCredentialsException();
        }

        if (!matchesPassword(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        String accessToken = generateAccessToken(user);
        return LoginResponse.builder().accessToken(accessToken).build();
    }

    private boolean matchesPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    private String generateAccessToken(User user) {
        return accessTokenEncoder.encode(
                AccessToken.builder()
                        .subject(user.getEmail())
                        .userType(user.getUserType().toString())
                        .userId(user.getId())
                        .build());
    }

}
