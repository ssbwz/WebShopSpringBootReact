package fontys.sem3.individualProject.business.impl;

import fontys.sem3.individualProject.business.AccessTokenEncoder;
import fontys.sem3.individualProject.business.exception.InvalidCredentialsException;
import fontys.sem3.individualProject.domain.*;
import fontys.sem3.individualProject.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepositoryMock;
    @Mock
    PasswordEncoder passwordEncoderMock;
    @Mock
    AccessTokenEncoder accessTokenEncoderMock;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void getUserById_WhenThereIsAValidUser_ReturnAUser() {
        //Arrange
        when(userRepositoryMock.findById(1L)).thenReturn(Optional.of(new User()));
        //Act
        Optional<User> user = userService.getUserById(1L);
        //Assert
        assertTrue(user.isPresent(), "I can't find existed user");
    }

    @Test
    public void createCustomer_WithValidData_ReturnNewCustomerId() {
        //Arrange
        CreateUserRequest request = CreateUserRequest.builder()
                .email("test@example.com")
                .password("password")
                .firstname("John")
                .lastname("Doe")
                .build();

        User customerBeforeSave = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .userType(UserType.Customer)
                .build();
        when(passwordEncoderMock.encode(customerBeforeSave.getPassword()))
                .thenReturn(customerBeforeSave.getPassword());
        User customerAfterSave = User.builder()
                .id(1L)
                .email(request.getEmail())
                .password(request.getPassword())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .userType(UserType.Customer)
                .build();
        when(userRepositoryMock.save(customerBeforeSave))
                .thenReturn(customerAfterSave);


        //Act
        CreateUserResponse response = userService.createCustomer(request);

        //Assert
        assertEquals(response.getUserId(), customerAfterSave.getId(), "The IDs aren" +
                "t matching");
        verify(userRepositoryMock).save(customerBeforeSave);
    }

    @Test
    public void login_whenCredentialsAreValid_ReturnValidToken() {
        //Arrange
        LoginRequest request = LoginRequest.builder().email("test@email.com").password("Password").build();
        when(userRepositoryMock.findByEmail(request.getEmail()))
                .thenReturn(User.builder().id(1L).email("test@email.com").userType(UserType.Customer).build());
        LoginResponse expected = LoginResponse.builder().accessToken("access_token").build();
        AccessToken accessToken = AccessToken.builder().userId(1L).userType("Customer").subject("test@email.com").build();
        when(accessTokenEncoderMock.encode(accessToken))
                .thenReturn(expected.getAccessToken());
        when(passwordEncoderMock.matches(request.getPassword(), null))
                .thenReturn(true);
        //Act
        LoginResponse actual = userService.login(request);
        //Assert
        assertEquals(expected, actual);
        verify(userRepositoryMock).findByEmail(request.getEmail());
        verify(accessTokenEncoderMock).encode(accessToken);
        verify(passwordEncoderMock).matches(request.getPassword(), null);
    }

    @Test
    public void login_whenCredentialsAreInValidAndUserDoesNotExist_ThrowsInvalidCredentialsException() {
        // Arrange
        when(userRepositoryMock.findByEmail("test@example.com")).thenReturn(null);

        LoginRequest loginRequest = LoginRequest.builder()
                .email("test@example.com")
                .password("password")
                .build();

        //Assert
        assertThrows(InvalidCredentialsException.class, () ->
                //Act
                userService.login(loginRequest)
        );
    }

    @Test
    public void login_whenCredentialsAreInValidAndUserDoesExist_ThrowsInvalidCredentialsException() {
        //Arrange
        LoginRequest request = LoginRequest.builder()
                .email("test@example.com")
                .password("password")
                .build();

        when(userRepositoryMock.findByEmail(request.getEmail()))
                .thenReturn(User.builder().password("encoded_password").build());

        when(passwordEncoderMock.matches(request.getPassword(), "encoded_password"))
                .thenReturn(false);


        //Assert
        assertThrows(InvalidCredentialsException.class, () ->
                //Act
                userService.login(request)
        );
    }

}


