package fontys.sem3.individualProject.domain;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class GetUserResponse {

    private Long id;
    private String email;
    private String firstname;
    private String lastname;
    private UserType userType;
}
