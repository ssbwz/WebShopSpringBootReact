package fontys.sem3.individualProject.business;


import fontys.sem3.individualProject.domain.AccessToken;

public interface AccessTokenEncoder {
    String encode(AccessToken accessToken);
}
