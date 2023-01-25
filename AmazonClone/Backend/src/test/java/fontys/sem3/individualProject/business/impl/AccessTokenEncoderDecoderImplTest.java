package fontys.sem3.individualProject.business.impl;

import fontys.sem3.individualProject.business.exception.InvalidAccessTokenException;
import fontys.sem3.individualProject.business.exception.InvalidCredentialsException;
import fontys.sem3.individualProject.domain.AccessToken;
import fontys.sem3.individualProject.domain.User;
import fontys.sem3.individualProject.domain.UserType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccessTokenEncoderDecoderImplTest {

    private AccessTokenEncoderDecoderImpl accessTokenEncoderDecoder = new AccessTokenEncoderDecoderImpl("E91E158E4C6656F68B1B5D1C316736DE98D2AD6EF3BFB4y4F78E9CFCDF5");

    @Test
    void encode_whenAccessTokenIsValid_ReturnAccessTokenString() {
        //Arrange
        User user = User.builder().id(1L).email("test@email.com").userType(UserType.Customer).build();
        AccessToken accessToken = AccessToken.builder()
                .subject(user.getEmail())
                .userId(user.getId())
                .userType(user.getUserType().toString())
                .build();
        //Act
        String actual  = accessTokenEncoderDecoder.encode(accessToken);
        //Assert
        assertTrue(!actual.isEmpty());
    }



    @Test
    void decode_whenAccessTokenIsValid_ReturnAccessToken() {
        //Arrange
        User user = User.builder().id(1L).email("test@email.com").userType(UserType.Customer).build();
        AccessToken expected = AccessToken.builder()
                .subject(user.getEmail())
                .userId(user.getId())
                .userType(user.getUserType().toString())
                .build();
        String accessTokenString  = accessTokenEncoderDecoder.encode(expected);
        //Act
        AccessToken actual = accessTokenEncoderDecoder.decode(accessTokenString);
        //Assert
        assertEquals(expected,actual,"It doesn't decode the accessTokenString in the right way");
    }

    @Test
    void decode_whenAccessTokenIsInvalid_ReturnAccessToken() {
        //Arrange
        User user = User.builder().id(1L).userType(UserType.Customer).build();
        AccessToken expected = AccessToken.builder()
                .subject(user.getEmail())
                .userId(user.getId())
                .userType(user.getUserType().toString())
                .build();
        String accessTokenString  = "defewfq3e";
        //Assert
        assertThrows(InvalidAccessTokenException.class, () ->
                //Act
                accessTokenEncoderDecoder.decode(accessTokenString)
        ,"It doesn't throw an exception when the accessTokenString is in valid");
    }
}