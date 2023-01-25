package fontys.sem3.individualProject.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CreateUserRequest {
    String email;
    String password;
    String firstname;
    String lastname;
}
