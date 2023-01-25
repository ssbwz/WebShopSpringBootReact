package fontys.sem3.individualProject.business;

import fontys.sem3.individualProject.domain.AccessToken;

public interface AccessTokenDecoder {
    AccessToken decode(String accessTokenEncoded);
}
