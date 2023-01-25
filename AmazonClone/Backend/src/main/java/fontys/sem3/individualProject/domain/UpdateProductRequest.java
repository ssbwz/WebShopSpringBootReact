package fontys.sem3.individualProject.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateProductRequest {
    final long  id;
    String name;
    String description;
    final double price;
    final Category category;
}
