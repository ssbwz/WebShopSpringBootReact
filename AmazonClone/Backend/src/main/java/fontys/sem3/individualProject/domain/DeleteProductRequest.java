package fontys.sem3.individualProject.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DeleteProductRequest {
    long productId;
    long creatorId;
    int decreasedQuantity;
}
