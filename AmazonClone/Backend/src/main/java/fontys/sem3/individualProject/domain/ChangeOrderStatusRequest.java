package fontys.sem3.individualProject.domain;

import fontys.sem3.individualProject.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ChangeOrderStatusRequest {
    long id;
    OrderStatus status;
}
