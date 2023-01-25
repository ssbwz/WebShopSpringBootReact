package fontys.sem3.individualProject.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendMessageRequest {
    long senderId;
    String question;
}
