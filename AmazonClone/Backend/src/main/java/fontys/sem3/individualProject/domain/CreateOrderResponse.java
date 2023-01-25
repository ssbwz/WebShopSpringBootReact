package fontys.sem3.individualProject.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateOrderResponse {
    private  long orderId;
}
