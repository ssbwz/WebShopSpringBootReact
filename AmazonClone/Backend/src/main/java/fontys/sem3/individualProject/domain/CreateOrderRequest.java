package fontys.sem3.individualProject.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class CreateOrderRequest {
    private long creatorId;
    private String street;
    private String houseNumber;
    private String postcode;
}
