package fontys.sem3.individualProject.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessToken {
    private String subject;
    private String userType;
    private Long userId;

}
