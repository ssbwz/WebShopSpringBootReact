package fontys.sem3.individualProject.domain;

import fontys.sem3.individualProject.domain.UserType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Reply {
    long id;
    String content;
    String dateTime;
    UserType userType;
}
